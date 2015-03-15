/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.redhat.playground.visualizer.service;

import it.redhat.playground.console.commands.*;
import it.redhat.playground.domain.Value;
import it.redhat.playground.visualizer.configuration.JDGDemo;
import it.redhat.playground.visualizer.console.CollectingUI;
import org.infinispan.manager.DefaultCacheManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JDGHelper {

    @Inject @JDGDemo
    private DefaultCacheManager cacheManager;

    @Inject
    private Logger log;

    String address() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing address command");
        new AddressConsoleCommand(cacheManager).execute(ui, null);
        return ui.getResult();
    }

    String get(String key) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        log.info("Executing key command");
        new GetConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String post(String key, String value) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        args.add(value);
        log.info("Executing put command");
        new PutConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String pia(String key, String value) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        args.add(value);
        log.info("Executing putIfAbsent command");
        new PutIfAbsentConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String locate(String key) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        log.info("Executing locate command");
        new LocateConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String info() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing info command");
        new InfoConsoleCommand(cacheManager).execute(ui, null);
        return ui.getResult();
    }

    String key() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing key command");
        new KeyConsoleCommand(cacheManager).execute(ui, null);
        return ui.getResult();
    }

    String loadtest() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing loadTest command");
        new LoadTestConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String local() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing local command");
        new LocalConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String primary() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing primary command");
        new PrimaryConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String all() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing all command");
        new AllConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String replica() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing replica command");
        new ReplicaConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String routing() {
        CollectingUI ui = new CollectingUI();
        log.info("Executing routing command");
        new RoutingConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String hashtags() {
        return "List all registered hashtags feeding the grid";
    }

}

