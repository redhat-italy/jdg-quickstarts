/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.console.commands;

import it.redhat.playground.JDG;
import it.redhat.playground.configuration.PlaygroundConfiguration;
import it.redhat.playground.console.UI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class WhoConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "who";
    private final Cache<Long, Value> cache;

    @Inject
    public WhoConsoleCommand(PlaygroundConfiguration conf) {
        this.cache = conf.getCache();
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Long id = Long.parseLong(args.next());
            console.println("Who am I for key " + id + "?");

            if (JDG.isPrimary(cache, id)) {
                console.println("PRIMARY");
            }

            if (JDG.isReadOwner(cache, id)) {
                console.println("READ OWNER");
            }

            if (JDG.isWriteOwner(cache, id)) {
                console.println("WRITE OWNER");
            }

            if (JDG.isWriteBackup(cache, id)) {
                console.println("WRITE BACKUP");
            }

        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: who <key>\nValue for key has to be a number. Example:\n locate 10");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: who <key>");
        }
        return true;
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME + " <key>");
        console.println("\t\tWhich is role for this node for an object.");
    }
}
