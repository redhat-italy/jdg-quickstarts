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
