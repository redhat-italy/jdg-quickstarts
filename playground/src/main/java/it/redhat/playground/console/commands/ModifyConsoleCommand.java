package it.redhat.playground.console.commands;

import it.redhat.playground.JDG;
import it.redhat.playground.console.UIConsole;
import it.redhat.playground.console.support.IllegalParametersException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ModifyConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "modify";

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UIConsole console, JDG jdg, Iterator<String> args) throws IllegalParametersException {
        try {
            Long id = Long.parseLong(args.next());
            String value = args.next();

            console.println(jdg.modify(id, value));
            console.println("Written (" + id + "," + value + ")");
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: modify <key> <value>\nValue for key has to be a number. In example\nmodify 10 test");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: modify <key> <value>");
        }
        return true;
    }

    @Override
    public void usage(UIConsole console) {
        console.println(COMMAND_NAME + " <key> <value>");
        console.println("\t\tModify an id object with value.");
    }
}
