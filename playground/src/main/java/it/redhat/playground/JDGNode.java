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

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import java.util.logging.Logger;

public class JDGNode {

    public JDGNode(String name) {
        this.name = name;
        initCacheManager();
    }

    public void initCacheManager() {
        if (manager == null) {
            log.info("DefaultCacheManager does not exist - constructing a new one");

            GlobalConfiguration glob = new GlobalConfigurationBuilder().clusteredDefault() // Builds a default clustered
                    // configuration
                    .transport().addProperty("configurationFile", "jgroups-udp.xml") // provide a specific JGroups configuration
                    .globalJmxStatistics().allowDuplicateDomains(true).enable() // This method enables the jmx statistics of
                            // the global configuration and allows for duplicate JMX domains
                    .build(); // Builds the GlobalConfiguration object
            Configuration loc = new ConfigurationBuilder().jmxStatistics().enable() // Enable JMX statistics
                    .clustering().cacheMode(CacheMode.DIST_SYNC) // Set Cache mode to DISTRIBUTED with SYNCHRONOUS replication
                    .hash().numOwners(2) // Keeps two copies of each key/value pair
                    //.expiration().lifespan(ENTRY_LIFESPAN) // Set expiration - cacheManager entries expire after some time (given by
                            // the lifespan parameter) and are removed from the cacheManager (cluster-wide).
                    .build();
            manager = new DefaultCacheManager(glob, loc, true);
        }
    }


    public DefaultCacheManager getManager() {
        return manager;
    }

    public String toString() {
        return name;
    }

    private DefaultCacheManager manager;
    private String name;
    private Logger log = Logger.getLogger(this.getClass().getName());

}
