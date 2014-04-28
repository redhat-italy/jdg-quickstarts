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

package it.redhat.playground;

import it.redhat.playground.console.TextUI;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws IOException {

        JDGNode node = null;

        if (args.length > 0) {
            node = new JDGNode(args[0]);
        } else {
            System.out.println("usage: node");
            System.exit(-1);
        }

        banner();
        log.info("Connecting to Node: " + node);
        new JDG().connect(node).attachUI(new TextUI(System.in, System.out)).processCommands();
    }

    private static void banner() {

        System.out.println("---------------------------------------");
        System.out.println("         Bankit JDG Testing CLI");
        System.out.println("            written by uL");
        System.out.println("---------------------------------------");
        System.out.println();

    }

    private static Logger log = Logger.getLogger(Main.class.getName());

}