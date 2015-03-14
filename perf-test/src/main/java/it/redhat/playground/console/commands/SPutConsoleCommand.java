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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SPutConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "sput";
    private final Cache<Long, Value> cache;

    public SPutConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Long id = Long.parseLong(args.next());
            String value = args.next();
            int size = Integer.parseInt(args.next());

            cache.put(id, new LargeValue(value, size));

            console.println("Written (" + id + "," + value + ") of " + size + " bytes");
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: put <key> <value> <size>\nValues for key and size have to be a number. In example\nsput 10 test 1024");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: put <key> <value> <size>");
        }
        return true;
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME + " <key> <value> <size>");
        console.println("\t\tPut an object (id, value) in the grid <size> bytes long.");
    }
}
