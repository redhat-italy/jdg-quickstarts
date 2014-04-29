package it.redhat.playground;

import it.redhat.playground.console.commands.ConsoleCommand;
import it.redhat.playground.console.commands.MapReduceConsoleCommand;

import java.util.ArrayList;
import java.util.List;

public class MapReduceConfiguration extends PlaygroundConfiguration {

    @Override
    protected List<ConsoleCommand> baseCommands() {
        ArrayList<ConsoleCommand> commands = new ArrayList<ConsoleCommand>(super.baseCommands());
        commands.add(new MapReduceConsoleCommand(this.cache));
        return commands;
    }
}
