/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.console.commands;

import it.redhat.playground.console.UI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.domain.LargeValue;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "test";
    private static final long STATISTICS_DELAY = 1000;
    private static final long DIVIDER_NANO_TO_MILLIS = 1000 * 1000;
    private static final long DIVIDER_NANO_SECONDS = 1000 * 1000 * 1000;
    public static final String REGEXP_DURATION = "^(\\d+)(s|m|h)$";
    public static final String REGEXP_SIZE = "^(\\d+)(k|K|m|M)?$";

    private final Cache<Long, Value> cache;

    private AtomicLong counter = new AtomicLong(0);
    private AtomicLong testDuration = new AtomicLong(0);

    public TestConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    private class TestTask implements Runnable {

        private long duration;
        private long maxCounter;
        private long size;

        private TestTask(long duration, long maxCounter, long size) {
            this.duration = duration;
            this.maxCounter = maxCounter;
            this.size = size;
        }

        @Override
        public void run() {
            LargeValue master = new LargeValue("data", (int) size);
            LargeValue value;
            long testStartTime = System.nanoTime();
            while ((System.nanoTime() - testStartTime) < duration) {
                long i = counter.incrementAndGet();
                value = new LargeValue(("" + i).getBytes(), master);
                cache.put(i % maxCounter, value);
            }
            testDuration.addAndGet(System.nanoTime() - testStartTime);
        }
    }

    private void runTest(UI console, int poolSize, long duration, long maxCounter, long size) {
        ForkJoinPool pool = new ForkJoinPool(poolSize);
        Timer timer = new Timer(true);
        timer.schedule(new ThroughputStatistics(console), STATISTICS_DELAY, STATISTICS_DELAY);
        cache.clear();
        try {
            for (int i = 0; i < poolSize; i++) {
                pool.execute(new TestTask(duration, maxCounter, size));
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
        } finally {
            timer.cancel();
            timer.purge();
            pool.shutdownNow();
        }
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {
        long duration = 0;
        long maxObjects = 0;
        try {
            String text = args.next();
            if (hasDuration(text)) {
                duration = parseDuration(text);
                maxObjects = Long.parseLong(args.next());
            } else {
                duration = Long.MAX_VALUE;
                maxObjects = Long.parseLong(text);
            }

            int size = parseSize(args.next());

            int poolSize = 1;
            if (args.hasNext()) {
                poolSize = Integer.parseInt(args.next());
                console.println(String.format("%tT - Using concurrent strategy with pool size %d", Calendar.getInstance(), poolSize));
            }

            if (args.hasNext()) {
                throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size> <poolSize>");
            }

            // WARMUP
            counter.set(0);
            testDuration.set(0);
            console.println(String.format("%tT - Warm up", Calendar.getInstance()));
            runTest(console, 1, 30 * DIVIDER_NANO_SECONDS, maxObjects, size);
            console.println(String.format("%tT - Stop warmup. Created %d elements", Calendar.getInstance(), counter.get()));
            console.println(String.format("%tT - Warmup duration %d in millis", Calendar.getInstance(), testDuration.get() / DIVIDER_NANO_TO_MILLIS));

            // TEST
            counter.set(0);
            testDuration.set(0);
            console.println(String.format("%tT - Started test.", Calendar.getInstance()));
            runTest(console, poolSize, duration, maxObjects, size);
            console.println(String.format("%tT - Stop test. Created %d elements", Calendar.getInstance(), counter.get()));
            console.println(String.format("%tT - Test duration %d in millis", Calendar.getInstance(), testDuration.get() / DIVIDER_NANO_TO_MILLIS));
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size> <poolSize>");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size> <poolSize>");
        }

        return true;
    }

    private int parseSize(String text) throws IllegalParametersException {
        int size = 0;
        Matcher sizeMatcher = Pattern.compile(REGEXP_SIZE).matcher(text);
        if (sizeMatcher.matches()) {
            size = Integer.parseInt(sizeMatcher.group(1));

            if (sizeMatcher.group(2) != null) {
                switch (sizeMatcher.group(2).charAt(0)) {
                    case 'm':
                    case 'M':
                        size *= 1024;
                    case 'k':
                    case 'K':
                        size *= 1024;
                }
            }
        } else {
            throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size> <poolSize>");
        }
        return size;
    }

    private long parseDuration(String text) throws IllegalParametersException {
        Matcher durationMatcher = Pattern.compile(REGEXP_DURATION).matcher(text);
        if (durationMatcher.matches()) {
            long duration = Long.parseLong(durationMatcher.group(1));
            switch (durationMatcher.group(2).charAt(0)) {
                case 'h':
                    duration *= 60;
                case 'm':
                    duration *= 60;
            }
            return duration * DIVIDER_NANO_SECONDS;
        } else {
            throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size>");
        }
    }

    private boolean hasDuration(String text) {
        return text.matches(REGEXP_DURATION);
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME + " <duration> <maxobjects> <size> <poolSize>");
        console.println("\t\tduration format is a number followed by a flag choose between s (seconds), m (minutes), h (hours)");
        console.println("\t\tmaxobjects format is a number");
        console.println("\t\tIf you've used a duration, this command will start a load test of indicated <duration> using values of <size> bytes long.");
    }

    private class ThroughputStatistics extends TimerTask {

        private final UI console;
        private volatile long lastStatistics;
        private volatile long lastCounter;

        public ThroughputStatistics(UI console) {
            this.console = console;
            this.lastStatistics = System.nanoTime();
            this.lastCounter = counter.get();
        }

        @Override
        public void run() {
            long elapsed = (System.nanoTime() - lastStatistics);
            if (elapsed > 0) {
                long objectCounter = counter.get() - lastCounter;

                console.println(String.format("%tT - Throughput %d object/s, for a total of %d in %d millis",
                        Calendar.getInstance(),
                        (int) (objectCounter / ((double) elapsed / DIVIDER_NANO_SECONDS)),
                        objectCounter,
                        elapsed / DIVIDER_NANO_TO_MILLIS));

                lastCounter = counter.get();
                lastStatistics = System.nanoTime();
            }
        }
    }
}
