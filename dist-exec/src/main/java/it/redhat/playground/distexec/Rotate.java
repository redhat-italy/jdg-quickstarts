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

package it.redhat.playground.distexec;

import it.redhat.playground.JDG;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Rotate implements DistributedCallable, Serializable {

    public Rotate(int shift) {
        this.shift = shift;
    }

    @Override
    public void setEnvironment(Cache cache, Set set) {
        this.cache = cache;
    }

    @Override
    public Set<Value> call() throws Exception {

        Set<Long> keys = cache.keySet();
        Set<Value> result = new HashSet<Value>();

        for (long key : keys) {
            if (JDG.checkIfCacheIsPrimaryFor(cache, key)) {
                Value v = rotate(cache.get(key));
                cache.put(key, v);
                result.add(v);
            }
        }
        return result;
    }

    public Value rotate(Value v) {
        shift = shift % 26 + 26;
        StringBuilder encoded = new StringBuilder();
        for (char i : v.toString().toLowerCase().toCharArray()) {
            if (Character.isLetter(i)) {
                int j = (i - 'a' + shift) % 26;
                encoded.append((char) (j + 'a'));
            } else {
                encoded.append(i);
            }
        }
        return new Value(encoded.toString());

    }

    private transient Cache<Long, Value> cache;
    private int shift;

}
