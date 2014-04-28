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
