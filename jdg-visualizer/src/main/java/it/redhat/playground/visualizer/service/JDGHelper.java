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

import it.redhat.playground.JDG;
import it.redhat.playground.console.UI;
import it.redhat.playground.console.commands.*;
import it.redhat.playground.domain.Value;
import it.redhat.playground.visualizer.configuration.JDGDemo;
import it.redhat.playground.visualizer.console.CollectingUI;
import it.redhat.playground.visualizer.dto.JDGNodeInfo;
import org.infinispan.Cache;
import org.infinispan.distexec.mapreduce.MapReduceTask;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.remoting.transport.Address;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

public class JDGHelper {

    @Inject
    @JDGDemo
    private DefaultCacheManager cacheManager;

    @Inject
    private Logger log;

    public UI address() {
        log.info("Executing address command");
        ConsoleCommand command = new AddressConsoleCommand(cacheManager);
        return commandExecutor(command, null);
    }

    public UI  get(String key) {
        log.info("Executing key command");
        ConsoleCommand command = new GetConsoleCommand(cacheManager.<Long, Value>getCache());
        Iterator<String> args = Arrays.asList(key).iterator();
        return commandExecutor(command, args);
    }

    public UI  post(String key, String value) {
        log.info("Executing put command");
        ConsoleCommand command = new PutConsoleCommand(cacheManager.<Long, Value>getCache());
        Iterator<String> args = Arrays.asList(key, value).iterator();
        return commandExecutor(command, args);
    }

    public UI  pia(String key, String value) {
        log.info("Executing putIfAbsent command");
        ConsoleCommand command = new PutIfAbsentConsoleCommand(cacheManager.<Long, Value>getCache());
        Iterator<String> args = Arrays.asList(key, value).iterator();
        return commandExecutor(command, args);
    }

    public UI  locate(String key) {
        log.info("Executing locate command");
        return commandExecutor(new LocateConsoleCommand(cacheManager.<Long, Value>getCache()), Arrays.asList(key).iterator());
    }

    public UI  info() {
        log.info("Executing info command");
        return commandExecutor(new InfoConsoleCommand(cacheManager), null);
    }

    public UI  key() {
        log.info("Executing key command");
        return commandExecutor(new KeyConsoleCommand(cacheManager), null);
    }

    public UI  loadtest() {
        log.info("Executing loadTest command");
        return commandExecutor(new LoadTestConsoleCommand(cacheManager.<Long, Value>getCache()), null);
    }

    public UI  local() {
        log.info("Executing local command");
        return commandExecutor(new LocalConsoleCommand(cacheManager.<Long, Value>getCache()), null);
    }

    public UI  primary() {
        log.info("Executing primary command");
        return commandExecutor(new PrimaryConsoleCommand(cacheManager.<Long, Value>getCache()), null);
    }

    public UI  all() {
        log.info("Executing all command");
        return commandExecutor(new AllConsoleCommand(cacheManager.<Long, Value>getCache()), null);
    }

    public UI  replica() {
        log.info("Executing replica command");
        return commandExecutor(new ReplicaConsoleCommand(cacheManager.<Long, Value>getCache()), null);
    }

    public UI  routing() {
        log.info("Executing routing command");
        return commandExecutor(new RoutingConsoleCommand(cacheManager.<Long, Value>getCache()), null);
    }

    public List<JDGNodeInfo> getJDGInfo() {
        Cache<Long, Value> cache = cacheManager.getCache();

        Map<String, Long> keyCountMap = new HashMap<>();
        Set<Long> keys = cache.keySet();
        for (Long k : keys) {
            List<Address> addresses = JDG.locate(cache, k);
            for (Address address : addresses) {
                if (keyCountMap.containsKey(address.toString())) {
                    keyCountMap.put(address.toString(), keyCountMap.get(address.toString()) + 1);
                } else {
                    keyCountMap.put(address.toString(), 1l);
                }
            }
        }

        List<JDGNodeInfo> result = new ArrayList<>();
        for(Map.Entry<String, Long> entry: keyCountMap.entrySet()) {
            result.add(new JDGNodeInfo(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public UI  hashtags() {
        CollectingUI ui = new CollectingUI();
        ui.println("List all registered hashtags feeding the grid");
        return ui;
    }

    private UI commandExecutor(ConsoleCommand command, Iterator<String> args) {
        CollectingUI ui = new CollectingUI();
        command.execute(ui, args);
        return ui;
    }
}

