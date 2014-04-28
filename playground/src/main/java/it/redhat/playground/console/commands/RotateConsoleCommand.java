package it.redhat.playground.console.commands;

import it.redhat.playground.JDG;
import it.redhat.playground.console.UIConsole;
import it.redhat.playground.console.support.IllegalParametersException;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RotateConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "rotate";

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UIConsole console, JDG jdg, Iterator<String> args) throws IllegalParametersException {
        try {
            Integer offset = Integer.parseInt(args.next());
            List<Future> results = jdg.rot(offset);

            console.println("Rotated all strings of " + offset + " characters");
            for (Future result : results) {
                try {
                    console.println(result.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: rotate <offset>\nValue for offset has to be a number. In example\nrotate 10");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: rotate <offset>");
        }
        return true;
    }

    @Override
    public void usage(UIConsole console) {
        console.println(COMMAND_NAME + " <offset>");
        console.println("\t\tGet an object from the grid.");
    }
}
