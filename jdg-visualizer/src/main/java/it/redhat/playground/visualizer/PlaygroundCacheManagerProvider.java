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

package it.redhat.playground.visualizer;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlaygroundCacheManagerProvider {

    //@Inject
    //private Logger log;

    private DefaultCacheManager manager;

    public DefaultCacheManager getCacheManager() {
        if (manager == null) {
            //log.info("\n\n DefaultCacheManager does not exist - constructing a new one\n\n");

            GlobalConfiguration glob = new GlobalConfigurationBuilder().clusteredDefault()
                    .transport().addProperty("configurationFile", "jgroups-tcp.xml")
                    .globalJmxStatistics().allowDuplicateDomains(true).enable()
                    .build();
            Configuration loc = new ConfigurationBuilder().jmxStatistics().enable()
                    .clustering().cacheMode(CacheMode.DIST_SYNC)
                    .hash().numOwners(2)
                    .build();
            manager = new DefaultCacheManager(glob, loc, true);
        }
        return manager;
    }

    @PreDestroy
    public void cleanUp() {
        manager.stop();
        manager = null;
    }

}
