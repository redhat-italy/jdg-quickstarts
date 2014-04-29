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

package it.redhat.infinispan.jmx;

import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.factories.annotations.Inject;
import org.infinispan.jmx.annotations.DataType;
import org.infinispan.jmx.annotations.MBean;
import org.infinispan.jmx.annotations.ManagedAttribute;
import org.infinispan.jmx.annotations.ManagedOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MBean(description = "Test Plugin", objectName = "InfinispanStatistics")
public class InfinispanStatistics {
    private Logger log = LoggerFactory.getLogger(InfinispanStatistics.class);

    @ManagedAttribute(writable = true, description = "Get test value", displayName = "GET_TEST", dataType = DataType.TRAIT)
    public String test;

    @ManagedOperation(displayName = "Display name test", name = "custom", description = "My custom description")
    public String getTest() {
        return test;
    }

    @Inject
    public void init(GlobalConfiguration globalConfiguration) {
        log.debug("Init Custom Statistics");
    }

    @Override
    public String toString() {
        return "InfinispanStatistics{" +
                "test='" + test + '\'' +
                '}';
    }
}
