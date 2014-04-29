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
import org.infinispan.Cache;

import java.util.Iterator;

public class LoadTestConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "loadtest";
    private final Cache<Long, Value> cache;

    public LoadTestConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
        cache.put(1l, new Value("Led Zeppelin"));
        cache.put(2l, new Value("Deep Purple"));
        cache.put(3l, new Value("Jethro Tull"));
        cache.put(4l, new Value("Pink Floyd"));
        cache.put(5l, new Value("Arctic Monkeys"));
        cache.put(6l, new Value("Franz Ferdinand"));
        cache.put(7l, new Value("Queen"));
        cache.put(8l, new Value("The Police"));
        cache.put(9l, new Value("Frank Zappa"));
        cache.put(10l, new Value("Dire Straits"));
        cache.put(11l, new Value("The Who"));
        cache.put(12l, new Value("Van Halen"));
        cache.put(13l, new Value("Jimi Hendrix"));
        cache.put(14l, new Value("Queens of the Stone Age"));
        cache.put(15l, new Value("Pearl Jam"));
        cache.put(16l, new Value("U2"));
        cache.put(17l, new Value("Lynyrd Skynyrd"));
        cache.put(18l, new Value("AC/DC"));
        cache.put(19l, new Value("Janis Joplin"));
        cache.put(20l, new Value("Prince"));

        console.println("Data grid loaded with example values.");
        return true;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME);
        console.println("\t\tLoad example values in the grid");
    }
}
