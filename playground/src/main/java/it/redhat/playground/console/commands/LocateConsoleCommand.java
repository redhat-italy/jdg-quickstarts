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

import it.redhat.playground.JDG;
import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LocateConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "locate";
    private final Cache<Long, Value> cache;

    public LocateConsoleCommand(Cache<Long, Value> cache) {
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

            console.println("Locating key " + id);
            List<Address> owners = JDG.locate(cache, id);
            for (int i = 0; i < owners.size(); i++) {

                Address address = owners.get(i);
                console.print(address);

                if (address.equals(cache.getCacheManager().getAddress())) {
                    console.print(" *");
                }

                if (i==0) {
                    console.println(" (primary)");
                } else {
                    console.println();
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: locate <key>\nValue for key has to be a number. Example:\n locate 10");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: locate <key>");
        }
        return true;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <key>");
        console.println("\t\tLocate an object in the grid.");
    }
}
