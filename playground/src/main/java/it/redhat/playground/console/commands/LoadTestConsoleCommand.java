package it.redhat.playground.console.commands;

import it.redhat.playground.JDG;
import it.redhat.playground.console.UIConsole;
import it.redhat.playground.console.support.IllegalParametersException;

import java.util.Iterator;

public class LoadTestConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "loadtest";

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UIConsole console, JDG jdg, Iterator<String> args) throws IllegalParametersException {
        jdg.put(1, "Led Zeppelin");
        jdg.put(2, "Deep Purple");
        jdg.put(3, "Jethro Tull");
        jdg.put(4, "Pink Floyd");
        jdg.put(5, "Arctic Monkeys");
        jdg.put(6, "Franz Ferdinand");
        jdg.put(7, "Queen");
        jdg.put(8, "The Police");
        jdg.put(9, "Frank Zappa");
        jdg.put(10, "Dire Straits");
        jdg.put(11, "The Who");
        jdg.put(12, "Van Halen");
        jdg.put(13, "Jimi Hendrix");
        jdg.put(14, "Queens of the Stone Age");
        jdg.put(15, "Pearl Jam");
        jdg.put(16, "U2");
        jdg.put(17, "Lynyrd Skynyrd");
        jdg.put(18, "AC/DC");
        jdg.put(19, "Janis Joplin");
        jdg.put(20, "Prince");

        console.println("Data grid loaded with example values.");
        return true;
    }

    @Override
    public void usage(UIConsole console) {
        console.println(COMMAND_NAME);
        console.println("\t\tLoad example values in the grid");
    }
}
