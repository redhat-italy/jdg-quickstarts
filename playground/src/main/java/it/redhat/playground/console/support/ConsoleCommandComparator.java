package it.redhat.playground.console.support;

import it.redhat.playground.console.commands.ConsoleCommand;

import java.util.Comparator;

public class ConsoleCommandComparator implements Comparator<ConsoleCommand> {

    @Override
    public int compare(ConsoleCommand o1, ConsoleCommand o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }

        return o1.command().compareTo(o2.command());
    }
}
