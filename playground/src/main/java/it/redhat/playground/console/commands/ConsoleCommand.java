package it.redhat.playground.console.commands;

import it.redhat.playground.JDG;
import it.redhat.playground.console.UIConsole;
import it.redhat.playground.console.support.IllegalParametersException;

import java.util.Iterator;

public interface ConsoleCommand {

    String command();

    boolean execute(UIConsole console, JDG jdg, Iterator<String> args) throws IllegalParametersException;

    void usage(UIConsole console);
}
