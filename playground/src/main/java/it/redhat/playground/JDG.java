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
import it.redhat.playground.distexec.Rotate;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.distexec.DistributedExecutorService;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.remoting.transport.Address;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class JDG {

    public JDG connect(JDGNode node) {

        cacheManager = node.getManager();
        cache = cacheManager.getCache();
        log.info("Connected to cacheManager: " + cache.toString());
        return this;
    }

    public TextUI attachUI(TextUI ui) {
        ui.setJdg(this);
        return ui;
    }

    public Set<String> keySet() {
        return valuesFromKeys(cache.keySet());
    }

    public Set<String> primaryKeySet() {
        return primaryValuesFromKeys(cache.keySet());
    }

    public Value get(long id) {
        return cache.get(id);
    }

    public Value put(long id, String value) {
        return cache.put(id, new Value(value));
    }

    public Value modify(long id, String value) {
        Value v = cache.get(id);
        return v.setVal(value);
    }

    public void clear() {
        cache.clear();
    }

    public List<Address> locate(long id) {
        return cache.getAdvancedCache().getDistributionManager().getConsistentHash().locateOwners(id);
    }

    public void shutdown() {
        cacheManager.stop();
    }

    public String info() {
        StringBuilder info = new StringBuilder();
        info.append("Cache Manager Status: ").append(cacheManager.getStatus()).append("\n");
        info.append("Cache Manager Address: ").append(cacheManager.getAddress()).append("\n");
        info.append("Coordinator address: ").append(cacheManager.getCoordinator()).append("\n");
        info.append("Is Coordinator: ").append(cacheManager.isCoordinator()).append("\n");
        info.append("Cluster Name: ").append(cacheManager.getClusterName()).append("\n");
        info.append("Member list: ").append(cacheManager.getMembers()).append("\n");
        info.append("Cache name: ").append(cache.toString()).append("\n");
        info.append("Cache size: ").append(cache.size()).append("\n");
        info.append("Cache status: ").append(cache.getStatus()).append("\n");
        info.append("Number of owners: ").append(cache.getAdvancedCache().getDistributionManager().getConsistentHash().getNumOwners()).append("\n");
        info.append("Number of segments: ").append(cache.getAdvancedCache().getDistributionManager().getConsistentHash().getNumSegments()).append("\n");
        return info.toString();
    }

    public String routingTable() {
        return cache.getAdvancedCache().getDistributionManager().getConsistentHash().getRoutingTableAsString();
    }

    public List<Future> rot(int offset) {
        DistributedExecutorService des = new DefaultExecutorService(cache);
        return des.submitEverywhere(new Rotate(offset));
    }

    public static boolean checkIfCacheIsPrimaryFor(Cache<Long, Value> cache, long key) {
        return cache.getAdvancedCache().getDistributionManager().getPrimaryLocation(key).equals(cache.getCacheManager().getAddress());
    }

    private Set<String> valuesFromKeys(Set<Long> keys) {
        Set<String> values = new HashSet<String>();
        for (long l : keys) {
            values.add(l + "," + get(l));
        }
        return values;
    }

    private Set<String> primaryValuesFromKeys(Set<Long> keys) {
        Set<String> values = new HashSet<String>();
        for (long l : keys) {
            if (checkIfCacheIsPrimaryFor(cache, l)) {
                values.add(l + "," + get(l));
            }
        }
        return values;
    }

    private EmbeddedCacheManager cacheManager;
    private Cache<Long, Value> cache;
    private Logger log = Logger.getLogger(this.getClass().getName());

}
