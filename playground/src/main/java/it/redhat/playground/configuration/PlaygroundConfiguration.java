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

package it.redhat.playground.configuration;

import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.commands.*;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.persistence.leveldb.configuration.LevelDBStoreConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PlaygroundConfiguration {

    private final static Logger log = LoggerFactory.getLogger(PlaygroundConfiguration.class);

    protected DefaultCacheManager manager;
    protected Cache<Long, Value> cache;

    private TextUI textUI;

    public final PlaygroundConfiguration configure() {
        banner();

        GlobalConfiguration glob = new GlobalConfigurationBuilder().clusteredDefault()
                .transport().addProperty("configurationFile", System.getProperty("playground.jgroups.configurationFile", "jgroups-udp.xml"))
                .globalJmxStatistics().allowDuplicateDomains(true).enable()
                .build();

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.jmxStatistics().enable();
        configureCacheMode(configurationBuilder);
        configureCacheStore(configurationBuilder);

        Configuration loc = configurationBuilder.build();

        manager = new DefaultCacheManager(glob, loc, true);
        cache = manager.getCache();

        textUI = new TextUI(System.in, System.out);
        for (ConsoleCommand command : baseCommands()) {
            textUI.register(command);
        }
        return this;
    }

    private void configureCacheStore(ConfigurationBuilder configurationBuilder) {
        String location = System.getProperty("playground.levelDB.location");
        String expiredLocation = System.getProperty("playground.levelDB.expired");
        if (location == null || location.isEmpty() || expiredLocation == null || expiredLocation.isEmpty()) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug(String.format("Configure levelDB store location=%s, expired=%s", location, expiredLocation));
        }
        LevelDBStoreConfigurationBuilder builder = configurationBuilder.persistence().passivation(false)
                .addStore(LevelDBStoreConfigurationBuilder.class)
                .location(location)
                .expiredLocation(expiredLocation);
        if (Boolean.valueOf(System.getProperty("playground.levelDB.writeBehind"))) {
            if (log.isDebugEnabled()) {
                log.debug("Configure levelDB store with Write-Behind strategy");
            }
            builder.async().enable();
        }
    }

    private void configureCacheMode(ConfigurationBuilder configurationBuilder) {
        CacheMode cacheMode = getCacheMode();
        if (cacheMode.isDistributed()) {
            configurationBuilder
                    .clustering().cacheMode(cacheMode)
                    .hash().numOwners(getNumOwners());
        } else {
            configurationBuilder
                    .clustering().cacheMode(cacheMode);
        }
    }

    private CacheMode getCacheMode() {
        try {
            return CacheMode.valueOf(System.getProperty("playground.cacheMode", "DIST_SYNC"));
        } catch (IllegalArgumentException e) {
            return CacheMode.DIST_SYNC;
        }
    }

    private int getNumOwners() {
        try {
            return Integer.valueOf(System.getProperty("playground.numOwners", "2"));
        } catch (IllegalArgumentException e) {
            return 2;
        }
    }

    public final void start() {
        try {
            textUI.start();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    protected List<ConsoleCommand> baseCommands() {
        return Arrays.asList(new ClearConsoleCommand(manager),
                new GetConsoleCommand(cache),
                new HelpConsoleCommand(),
                new InfoConsoleCommand(manager),
                new LoadTestConsoleCommand(cache),
                new LocalConsoleCommand(cache),
                new LocateConsoleCommand(cache),
                new PutIfAbsentConsoleCommand(cache),
                new PrimaryConsoleCommand(cache),
                new PutConsoleCommand(cache),
                new QuitConsoleCommand(manager),
                new RoutingConsoleCommand(cache));
    }

    protected void banner() {
        System.out.println("---------------------------------------");
        System.out.println("           JDG Playground CLI");
        System.out.println("---------------------------------------");
        System.out.println();
    }

}
