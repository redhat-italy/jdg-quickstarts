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
import it.redhat.playground.domain.Value;
import it.redhat.playground.mapreduce.MapperCountGroup;
import it.redhat.playground.mapreduce.ReducerCountGroup;
import org.infinispan.Cache;
import org.infinispan.distexec.mapreduce.MapReduceTask;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MapReduceConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "count";
    private Cache<Long, Value> cache;

    public MapReduceConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Integer limit = Integer.parseInt(args.next());

            Future<Map<String, Long>> future = new MapReduceTask<Long, Value, String, Long>(cache)
                    .mappedWith(new MapperCountGroup(limit))
                    .reducedWith(new ReducerCountGroup())
                    .executeAsynchronously();

            long before = System.currentTimeMillis();

            try {
                Map<String, Long> map = future.get();
                console.println("Count results:");
                for (String key : map.keySet()) {
                    console.println(String.format("\t%s = %d", key, map.get(key)));
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            long after = System.currentTimeMillis();
            console.println("Total time: " + (after - before));

        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: count <threshold>\nValue for threshold has to be a number. Example:\n count 4");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: count <threshold>");
        }
        return true;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <threshold>");
        console.println("\t\tReturn how many group's names are shorter/longer than the threshold");
    }
}
