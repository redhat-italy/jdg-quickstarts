package it.redhat.playground;

import it.redhat.playground.console.commands.ConsoleCommand;
import it.redhat.playground.console.commands.SPutConsoleCommand;

import java.util.ArrayList;
import java.util.List;

public class PerfTestConfiguration extends PlaygroundConfiguration {

    @Override
    protected List<ConsoleCommand> baseCommands() {
        ArrayList<ConsoleCommand> commands = new ArrayList<ConsoleCommand>(super.baseCommands());
        commands.add(new SPutConsoleCommand(this.cache));
        return commands;
    }
}
