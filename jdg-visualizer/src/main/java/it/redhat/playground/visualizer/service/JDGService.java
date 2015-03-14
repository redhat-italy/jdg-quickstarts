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
import it.redhat.playground.visualizer.console.CollectingUI;
import org.infinispan.manager.DefaultCacheManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class JDGService {

    @Inject
    private DefaultCacheManager cacheManager;

    String address() {
        CollectingUI ui = new CollectingUI();
        new AddressConsoleCommand(cacheManager).execute(ui, null);
        return ui.getResult();
    }

    String get(String key) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        new GetConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String post(String key, String value) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        args.add(value);
        new PutConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String pia(String key, String value) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        args.add(value);
        new PutIfAbsentConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String locate(String key) {
        CollectingUI ui = new CollectingUI();
        List<String> args = new ArrayList<>();
        args.add(key);
        new LocateConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, args.iterator());
        return ui.getResult();
    }

    String hashtags() {
        return "List all registered hashtags feeding the grid";
    }

    String info() {
        CollectingUI ui = new CollectingUI();
        new InfoConsoleCommand(cacheManager).execute(ui, null);
        return ui.getResult();
    }

    String key() {
        CollectingUI ui = new CollectingUI();
        new KeyConsoleCommand(cacheManager).execute(ui, null);
        return ui.getResult();
    }

    String loadtest() {
        CollectingUI ui = new CollectingUI();
        new LoadTestConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String local() {
        CollectingUI ui = new CollectingUI();
        new LocalConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String primary() {
        CollectingUI ui = new CollectingUI();
        new PrimaryConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String all() {
        CollectingUI ui = new CollectingUI();
        new AllConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String replica() {
        CollectingUI ui = new CollectingUI();
        new ReplicaConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

    String routing() {
        CollectingUI ui = new CollectingUI();
        new RoutingConsoleCommand(cacheManager.<Long, Value>getCache()).execute(ui, null);
        return ui.getResult();
    }

}

