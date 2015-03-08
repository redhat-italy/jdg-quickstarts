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
import it.redhat.playground.domain.SimpleValue;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PutIfAbsentConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "putIfAbsent|putifabsent|pia";
    private final Cache<Long, Value> cache;

    public PutIfAbsentConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Long id = Long.parseLong(args.next());

            Value newValue = new SimpleValue(args.next());

            Value value = cache.putIfAbsent(id, newValue);
            if (value == null) {
                console.println("Written (" + id + "," + newValue + ")");
            } else {
                console.println("Already associated (" + id + "," + value + ")");
            }
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: putIfAbsent <key> <value>\nValue for key has to be a number. Example:\n putIfAbsent 10 test");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: putIfAbsent <key> <value>");
        }
        return true;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <key> <value>");
        console.println("\t\tPut an object (id, value) in the grid if not already present");
    }
}
