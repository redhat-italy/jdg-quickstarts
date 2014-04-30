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

    private void runTest(TextUI console, long duration, long maxCounter, long size, long nanosPerOp) {
        testDuration = 0;
        long warmupStartTime = System.nanoTime();
        console.println(String.format("%tT - Warm up", Calendar.getInstance()));
        warmup(10, maxCounter < Long.MAX_VALUE ? (int) maxCounter : 1000, (int) size);
        warmupDuration = System.nanoTime() - warmupStartTime;
        console.println(String.format("%tT - End warm up. Duration %d in millis", Calendar.getInstance(), warmupDuration / DIVIDER_NANO_TO_MILLIS));

        LargeValue master = new LargeValue("data", (int) size);
        LargeValue value;
        for (int i = 0; i < 100; i++) {
            Timer timer = new Timer(true);
            timer.schedule(new ThroughputStatistics(console), STATISTICS_DELAY, STATISTICS_DELAY);
            cache.clear();

            counter = 0;
            console.println(String.format("Started %tT. Cycle %d of %d", Calendar.getInstance(), i, 100));

            long testStartTime = System.nanoTime();
            long nextOp = testStartTime + nanosPerOp;
            while ((counter < maxCounter) && ((System.nanoTime() - testStartTime) < duration)) {
                value = new LargeValue(("" + counter).getBytes(), master);

                counter += 1;
                cache.put(counter, value);

                long nanoSleep = nextOp - System.nanoTime();
                if ((nanosPerOp > 0) && (nanoSleep > 0)) {
                    try {
                        Thread.sleep(nanoSleep / DIVIDER_NANO_TO_MILLIS, (int) (nanoSleep % DIVIDER_NANO_TO_MILLIS));
                    } catch (InterruptedException e) {
                    }
                }
                nextOp += nanosPerOp;
            }
            console.println(String.format("%tT - Stop test. Created %d elements", Calendar.getInstance(), counter));
            console.println(String.format("%tT - Test duration %d in millis", Calendar.getInstance(), (System.nanoTime() - testStartTime) / DIVIDER_NANO_TO_MILLIS));
            testDuration += System.nanoTime() - testStartTime;
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
        long nanoDuration = 0;
        long maxObjects = 0;
        long nanoRate = 0;
        try {
            String durationOrMaxObjects = args.next();
            Matcher durationMatcher = Pattern.compile("^(\\d+)(s|m|h)?$").matcher(durationOrMaxObjects);
            if (durationMatcher.matches()) {
                if (durationMatcher.group(2) == null) {
                    maxObjects = Long.parseLong(durationMatcher.group(1));
                    nanoDuration = Long.MAX_VALUE;
                } else {
                    maxObjects = Long.MAX_VALUE;
                    nanoDuration = Long.parseLong(durationMatcher.group(1)) * DIVIDER_NANO_SECONDS;
                    switch (durationMatcher.group(2).charAt(0)) {
                        case 'h':
                            nanoDuration *= 60;
                        case 'm':
                            nanoDuration *= 60;
                    }
                }
            } else {
                throw new IllegalParametersException("Expected usage: test <duration|maxobjects> <size> <rate>");
            }

            Matcher sizeMatcher = Pattern.compile("^(\\d+)(k|K|m|M)?$").matcher(args.next());
            int size = 0;
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
                throw new IllegalParametersException("Expected usage: test <duration|maxobjects> <size> <rate>");
            }

            long opPerSecond = 0;
            if (args.hasNext()) {
                opPerSecond = Long.parseLong(args.next());
                nanoRate = DIVIDER_NANO_SECONDS / opPerSecond;
            }

            console.println(String.format("%tT - Start test %s of %d bytes at %d rate per second", Calendar.getInstance(), durationOrMaxObjects, size, opPerSecond));
            runTest(console, nanoDuration, maxObjects, size, nanoRate);
            console.println(String.format("%tT - Total Test duration %d in millis", Calendar.getInstance(), testDuration / DIVIDER_NANO_TO_MILLIS));
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: test <duration|maxobjects> <size> <rate>");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: test <duration|maxobjects> <size> <rate>");
        }
    }

    private LargeValue[] warmup(int cycles, int setSize, int objectSize) {
        LargeValue[] values = new LargeValue[setSize];
        for (int j = 0; j < cycles; j++) {
            cache.clear();
            for (int i = 0; i < values.length; i++) {
                if (j == 0) {
                    values[i] = new LargeValue("data" + i, objectSize);
                }
                cache.put((long)i, values[i]);
            }
        }
        return values;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <duration|maxobjects> <size> <rate>");
        console.println("\t\tduration format is a number followed by a flag choose between s (seconds), m (minutes), h (hours)");
        console.println("\t\tmaxobjects format is a number");
        console.println("\t\tIf you've used a duration, this command will start a load test of indicated <duration> using values of <size> bytes long at the desidered <rate> per seconds.");
        console.println("\t\tOtherwise, this command will start a load test till reach the <maxobjects> using values of <size> bytes long at the desidered <rate> per seconds.");
        console.println("\t\tIf rate is not specified, TEST will try as much as possibile.");
    }

    private class ThroughputStatistics extends TimerTask {

        private final TextUI console;
        private volatile long lastStatistics;
        private volatile long lastCounter;

        public ThroughputStatistics(TextUI console) {
            this.console = console;
            this.lastStatistics = System.nanoTime();
            this.lastCounter = 0;
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
