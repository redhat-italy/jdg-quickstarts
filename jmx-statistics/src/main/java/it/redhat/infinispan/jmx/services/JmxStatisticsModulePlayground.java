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

package it.redhat.infinispan.jmx.services;

import it.redhat.infinispan.jmx.InfinispanStatistics;
import it.redhat.infinispan.jmx.support.MBeanUtils;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.factories.GlobalComponentRegistry;
import org.infinispan.jmx.JmxUtil;
import org.infinispan.lifecycle.AbstractModuleLifecycle;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class JmxStatisticsModulePlayground extends AbstractModuleLifecycle {

    private ObjectName objName;

    @Override
    public void cacheManagerStarting(GlobalComponentRegistry gcr, GlobalConfiguration globalConfiguration) {
        MBeanServer mbeanServer = JmxUtil.lookupMBeanServer(globalConfiguration);
        String jmxDomain = globalConfiguration.globalJmxStatistics().domain();

        objName = MBeanUtils.<InfinispanStatistics>builder(gcr)
                .withDomain(jmxDomain)
                .withType("custom-statistics")
                .register(new InfinispanStatistics(), InfinispanStatistics.class)
                .on(mbeanServer);
    }

    @Override
    public void cacheManagerStopping(GlobalComponentRegistry gcr) {
        MBeanUtils.unregister(gcr, objName);
    }
}
