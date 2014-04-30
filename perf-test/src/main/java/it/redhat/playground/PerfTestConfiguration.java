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

package it.redhat.playground;

import it.redhat.playground.console.commands.ConsoleCommand;
import it.redhat.playground.console.commands.SPutConsoleCommand;
import it.redhat.playground.console.commands.SizeConsoleCommand;
import it.redhat.playground.console.commands.TestConsoleCommand;

import java.util.ArrayList;
import java.util.List;

public class PerfTestConfiguration extends PlaygroundConfiguration {

    @Override
    protected List<ConsoleCommand> baseCommands() {
        ArrayList<ConsoleCommand> commands = new ArrayList<ConsoleCommand>(super.baseCommands());
        commands.add(new SPutConsoleCommand(this.cache));
        commands.add(new SizeConsoleCommand(this.cache));
        commands.add(new TestConsoleCommand(this.cache));
        return commands;
    }
}
