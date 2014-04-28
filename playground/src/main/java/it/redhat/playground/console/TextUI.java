/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.console;

import it.redhat.playground.JDG;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class TextUI {

    public TextUI(InputStream in, PrintStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = out;
    }

    public void processCommands() throws IOException {

        boolean keepRunning = true;
        while(keepRunning) {
            out.print("> ");
            out.flush();
            String line = in.readLine();
            if(line == null) {
                break;
            }
            keepRunning = processLine(line);
        }
    }

    public void setJdg(JDG jdg) {
        this.jdg = jdg;
    }

    private boolean processLine(String line) {
        Scanner scanner = new Scanner(line);

        if(readCommand(scanner, "put") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            String value = scanner.next();
            jdg.put(id, value);
            out.println("Written (" + id + "," + value + ")");
        }

        else if(readCommand(scanner, "get") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            out.println(jdg.get(id));
        }

        else if(readCommand(scanner, "locate") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            out.println(jdg.locate(id));
        }

        else if(readCommand(scanner, "modify") && scanner.hasNext()) {
            Long id = Long.parseLong(scanner.next());
            String value = scanner.next();
            out.println(jdg.modify(id, value));
            out.println("Modified (" + id + "," + value + ")");
        }

        else if(readCommand(scanner, "loadtest")) {

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
            out.println("Data grid loaded with example values.");

        }

        else if(readCommand(scanner, "local")) {
            printValues(jdg.keySet());
        }

        else if(readCommand(scanner, "primary")) {
            printValues(jdg.primaryKeySet());
        }

        else if(readCommand(scanner, "clear")) {
            jdg.clear();
            out.println("Data grid cleared.");
        }

        else if(readCommand(scanner, "info")) {
            out.print(jdg.info());
        }

        else if(readCommand(scanner, "routing")) {
            out.println(jdg.routingTable());
        }

        else if(readCommand(scanner, "rotate") && scanner.hasNext()) {
            Integer offset = Integer.parseInt(scanner.next());
            List<Future> results = jdg.rot(offset);
            out.println("Rotated all strings of " + offset + " characters");

            for (Future result:results) {
                try {
                    out.println(result.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        else if (readCommand(scanner, "exit|quit|q|x")) {
            out.println("Shutting down...");
            jdg.shutdown();
            return false;
        }

        else if(readCommand(scanner, "help")) {
            usage();
        }

        else {
            out.println("> ");
        }

        return true;
    }

    private void printValues(Set<String> values) {
        for(String value : values) {
            out.println(value);
        }
    }

    private void usage() {
        out.println("Commands:");
        out.println("get id");
        out.println("     Get an object from the grid.");
        out.println("put id value");
        out.println("     Put an object (id, value) in the grid.");
        out.println("modify id value");
        out.println("     Modify an id object with value.");
        out.println("locate id");
        out.println("     Locate an object in the grid.");
        out.println("loadtest");
        out.println("     Load example values in the grid");
        out.println("local");
        out.println("     List all local valuesFromKeys.");
        out.println("primary");
        out.println("     List all local valuesFromKeys for which this node is primary.");
        out.println("clear");
        out.println("     Clear all valuesFromKeys.");
        out.println("info");
        out.println("     Information on cache.");
        out.println("rotate n");
        out.println("     Apply a rotate n on String values with a Distributed Executor");
        out.println("routing");
        out.println("     Print routing table.");
        out.println("help");
        out.println("     List of commands.");
        out.println("exit|quit|q|x");
        out.println("     Exit the shell.");
    }

    private boolean readCommand(Scanner scanner, String command) {
        if (scanner.hasNext(command)) {
            scanner.next(command);
            return true;
        }
        return false;
    }

    private JDG jdg;
    private final BufferedReader in;
    private final PrintStream out;
    private Logger log = Logger.getLogger(this.getClass().getName());

}