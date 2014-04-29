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

package it.redhat.playground.mapreduce;

import it.redhat.playground.domain.Value;
import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;

public class MapperCountGroup implements Mapper<Long, Value, String, Long> {

    private static final String SHORTER = "shorter";
    private static final String LONGER = "longer";
    private int limit;

    public MapperCountGroup(int limit) {
        this.limit = limit;
    }

    @Override
    public void map(Long key, Value value, Collector<String, Long> collector) {
        if(value.toString().length()<limit) {
            collector.emit(SHORTER, 1l);
        } else {
            collector.emit(LONGER, 1l);
        }
    }
}
