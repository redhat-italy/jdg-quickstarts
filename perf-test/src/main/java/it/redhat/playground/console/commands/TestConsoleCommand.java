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

import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.domain.LargeValue;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;

import java.util.*;
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

    private long counter;
    private long warmupDuration;
    private long testDuration;
    private long totalTestDuration;

    public TestConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    private void runTest(TextUI console, long duration, long maxCounter, long size) {
        Timer timer = new Timer(true);
        timer.schedule(new ThroughputStatistics(console), STATISTICS_DELAY, STATISTICS_DELAY);

        cache.clear();
        LargeValue master = new LargeValue("data", (int) size);
        LargeValue value;
        long testStartTime = System.nanoTime();
        while ((System.nanoTime() - testStartTime) < duration) {
            counter += 1;
            value = new LargeValue(("" + counter).getBytes(), master);
            cache.put(counter % maxCounter, value);
        }
        testDuration += System.nanoTime() - testStartTime;
        timer.cancel();
        timer.purge();
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
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

            if (args.hasNext()) {
                throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size>");
            }

            // WARMUP
            counter = 0;
            testDuration = 0;
            console.println(String.format("%tT - Warm up", Calendar.getInstance()));
            runTest(console, 30 * DIVIDER_NANO_SECONDS, maxObjects, size);
            console.println(String.format("%tT - Stop warmup. Created %d elements", Calendar.getInstance(), counter));
            console.println(String.format("%tT - Warmup duration %d in millis", Calendar.getInstance(), testDuration / DIVIDER_NANO_TO_MILLIS));

            // TEST
            counter = 0;
            testDuration = 0;
            console.println(String.format("%tT - Started test.", Calendar.getInstance()));
            runTest(console, duration, maxObjects, size);
            console.println(String.format("%tT - Stop test. Created %d elements", Calendar.getInstance(), counter));
            console.println(String.format("%tT - Test duration %d in millis", Calendar.getInstance(), testDuration / DIVIDER_NANO_TO_MILLIS));
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size>");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size>");
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
            throw new IllegalParametersException("Expected usage: test <duration> <maxobjects> <size>");
        }
        return size;
    }

    private long parseDuration(String text) throws IllegalParametersException {
        Matcher durationMatcher = Pattern.compile(REGEXP_DURATION).matcher(text);
        if(durationMatcher.matches()) {
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
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <duration> <maxobjects> <size> <rate>");
        console.println("\t\tduration format is a number followed by a flag choose between s (seconds), m (minutes), h (hours)");
        console.println("\t\tmaxobjects format is a number");
        console.println("\t\tIf you've used a duration, this command will start a load test of indicated <duration> using values of <size> bytes long.");
    }

    private class ThroughputStatistics extends TimerTask {

        private final TextUI console;
        private volatile long lastStatistics;
        private volatile long lastCounter;

        public ThroughputStatistics(TextUI console) {
            this.console = console;
            this.lastStatistics = System.nanoTime();
            this.lastCounter = counter;
        }

        @Override
        public void run() {
            long elapsed = (System.nanoTime() - lastStatistics);
            if (elapsed > 0) {
                long objectCounter = counter - lastCounter;

                console.println(String.format("%tT - Throughput %d object/s, for a total of %d in %d millis",
                        Calendar.getInstance(),
                        (int) (objectCounter / ((double) elapsed / DIVIDER_NANO_SECONDS)),
                        objectCounter,
                        elapsed / DIVIDER_NANO_TO_MILLIS));

                lastCounter = counter;
                lastStatistics = System.nanoTime();
            }
        }
    }
}
