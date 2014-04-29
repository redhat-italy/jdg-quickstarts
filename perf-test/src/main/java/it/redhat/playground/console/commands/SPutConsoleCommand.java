package it.redhat.playground.console.commands;

import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.domain.SimpleValue;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SPutConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "sput";
    private final Cache<Long, Value> cache;

    public SPutConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Long id = Long.parseLong(args.next());
            String value = args.next();
            Long size = Long.parseLong(args.next());

            cache.put(id, new SimpleValue(value));

            console.println("Written (" + id + "," + value + ")");
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: put <key> <value> <size>\nValues for key and size have to be a number. In example\nsput 10 test 1024");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: put <key> <value> <size>");
        }
        return true;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <key> <value> <size>");
        console.println("\t\tPut an object (id, value) in the grid <size> bytes long.");
    }
}
