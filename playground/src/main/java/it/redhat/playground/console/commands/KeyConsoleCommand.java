/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.console.commands;

import it.redhat.playground.configuration.PlaygroundConfiguration;
import it.redhat.playground.console.UI;
import it.redhat.playground.console.support.IllegalParametersException;
import org.infinispan.Cache;
import org.infinispan.affinity.KeyAffinityService;
import org.infinispan.affinity.KeyAffinityServiceFactory;
import org.infinispan.affinity.impl.RndKeyGenerator;
import org.infinispan.manager.DefaultCacheManager;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "key";
    private DefaultCacheManager cacheManager;

    @Inject
    public KeyConsoleCommand(PlaygroundConfiguration conf) {
        this.cacheManager = conf.getCacheManager();
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {

        Cache cache = cacheManager.getCache();
        ExecutorService service = Executors.newSingleThreadExecutor();
        KeyAffinityService keyAffinityService = KeyAffinityServiceFactory.newLocalKeyAffinityService(cache, new RndKeyGenerator(),
               service , 100);
        Object localKey = keyAffinityService.getKeyForAddress(cacheManager.getAddress());
        keyAffinityService.stop();
        service.shutdown();
        console.println(localKey);
        return true;

    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME);
        console.println("\t\tGet a key which is affine to this cluster node");
    }
}
