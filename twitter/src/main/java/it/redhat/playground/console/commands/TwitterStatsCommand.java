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

import it.redhat.playground.console.UI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.twitter.TwitterService;
import it.redhat.playground.twitter.TwitterServices;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwitterStatsCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "stats";

    public TwitterStatsCommand() {
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {
        try {
            String hashtag = args.next();
            TwitterService service = TwitterServices.getService(hashtag);
            if (service != null) {
                console.println(service.stats());
            } else {
                console.println("No hashtag #" + hashtag + " registered");
            }
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: stop <hashtag>");
        }
        return true;
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME+ " <hashtag>");
        console.println("\t\tStats on tweets received with <hashtag>");
    }
}
