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

import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JDG {

    public static String routingTable(Cache<Long, Value> cache) {
        return cache.getAdvancedCache().getDistributionManager().getConsistentHash().getRoutingTableAsString();
    }

    public static List<Address> locate(Cache<Long, Value> cache, long id) {
        return cache.getAdvancedCache().getDistributionManager().getConsistentHash().locateOwners(id);
    }

    public static boolean checkIfCacheIsPrimaryFor(Cache<Long, Value> cache, long key) {
        return cache.getAdvancedCache().getDistributionManager().getPrimaryLocation(key).equals(cache.getCacheManager().getAddress());
    }

    public static Set<String> valuesFromKeys(Cache<Long, Value> cache) {
        return valuesFromKeys(cache, Filter.ALL);
    }

    public static Set<String> primaryValuesFromKeys(Cache<Long, Value> cache) {
        return valuesFromKeys(cache, Filter.PRIMARY);
    }

    public static Set<String> replicaValuesFromKeys(Cache<Long, Value> cache) {
        return valuesFromKeys(cache, Filter.REPLICA);
    }

    private static Set<String> valuesFromKeys(Cache<Long, Value> cache, Filter filter) {
        Set<String> values = new HashSet<String>();

        for (Long l : cache.keySet()) {
            switch (filter) {
                case ALL:
                    values.add(l + "," + cache.get(l));
                    break;
                case PRIMARY:
                    if (checkIfCacheIsPrimaryFor(cache, l)) {
                        values.add(l + "," + cache.get(l));
                    }
                    break;
                case REPLICA:
                    if (!checkIfCacheIsPrimaryFor(cache, l)) {
                        values.add(l + "," + cache.get(l));
                    }
                    break;
            }
        }
        return values;
    }

    private static  final Logger log = LoggerFactory.getLogger(JDG.class);

    private static enum Filter {ALL, PRIMARY, REPLICA};

}
