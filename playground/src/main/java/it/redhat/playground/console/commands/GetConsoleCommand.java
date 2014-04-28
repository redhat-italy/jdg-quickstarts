package it.redhat.playground.console.commands;

import it.redhat.playground.JDG;
import it.redhat.playground.console.UIConsole;
import it.redhat.playground.console.support.IllegalParametersException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GetConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "get";

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UIConsole console, JDG jdg, Iterator<String> args) throws IllegalParametersException {
        try {
            Long id = Long.parseLong(args.next());

            console.println(jdg.get(id));
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: get <key>\nValue for key has to be a number. In example\nget 10");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: get <key>");
        }
        return true;
    }

    @Override
    public void usage(UIConsole console) {
        console.println(COMMAND_NAME + " <key>");
        console.println("\t\tGet an object from the grid.");
    }
}
