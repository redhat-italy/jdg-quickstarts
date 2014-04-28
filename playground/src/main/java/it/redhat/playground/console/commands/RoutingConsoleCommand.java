package it.redhat.playground.console.commands;

import it.redhat.playground.JDG;
import it.redhat.playground.console.UIConsole;
import it.redhat.playground.console.support.IllegalParametersException;

import java.util.Iterator;

public class RoutingConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "routing";

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UIConsole console, JDG jdg, Iterator<String> args) throws IllegalParametersException {
        console.println(jdg.routingTable());
        return true;
    }

    @Override
    public void usage(UIConsole console) {
        console.println(COMMAND_NAME);
        console.println("\t\tPrint routing table.");
    }
}
