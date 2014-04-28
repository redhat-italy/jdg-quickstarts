package it.redhat.infinispan.jmx.support;

import org.infinispan.factories.GlobalComponentRegistry;
import org.infinispan.factories.components.ManageableComponentMetadata;
import org.infinispan.jmx.JmxUtil;
import org.infinispan.jmx.ResourceDMBean;
import org.infinispan.jmx.annotations.MBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class MBeanUtils {

    private static final Logger log = LoggerFactory.getLogger(MBeanUtils.class);

    public static <T> Builder<T> builder(GlobalComponentRegistry gcr) {
        return new Builder<T>(gcr);
    }

    public static void unregister(GlobalComponentRegistry gcr, ObjectName objName) {
        if (objName != null) {
            MBeanServer mbeanServer = JmxUtil.lookupMBeanServer(gcr.getGlobalConfiguration());
            try {
                JmxUtil.unregisterMBean(objName, mbeanServer);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    public static class Builder<T> {
        private String domain = null;
        private String name = null;
        private String component = null;
        private T object = null;
        private Class<T> clazz = null;

        private GlobalComponentRegistry gcr;
        private String type;

        public Builder(GlobalComponentRegistry gcr) {
            this.gcr = gcr;
        }

        public Builder<T> withDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder<T> withType(String type) {
            this.type = type;
            return this;
        }

        public Builder<T> withName(String name) {
            this.name = name;
            return this;
        }

        public Builder<T> register(T object, Class<T> clazz) {
            this.object = object;
            this.clazz = clazz;
            return this;
        }

        public ObjectName on(MBeanServer server) {
            MBean annotation = clazz.getAnnotation(MBean.class);
            if (name == null) {
                name = annotation.objectName();
            } else {
                component = annotation.objectName();
            }

            if ((server != null) && (domain != null) && (type != null) && (object != null) && (clazz != null)) {
                gcr.registerComponent(object, clazz);

                // Pick up metadata from the component metadata repository
                ManageableComponentMetadata metadata = gcr
                        .getComponentMetadataRepo()
                        .findComponentMetadata(clazz)
                        .toManageableComponentMetadata();

                // And use this metadata when registering the transport as a dynamic MBean
                ObjectName objectName;
                try {
                    ResourceDMBean mbean = new ResourceDMBean(object, metadata);
                    if (component != null) {
                        objectName = new ObjectName(String.format("%s:type=%s,name=%s,component=%s", domain, type, name, component));
                    } else {
                        objectName = new ObjectName(String.format("%s:type=%s,name=%s", domain, type, name));
                    }
                    JmxUtil.registerMBean(mbean, objectName, server);

                    return objectName;
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return null;
                }
            } else {
                throw new IllegalStateException(String.format("Domain[%s], Type[%s], Object[%s], Class[%s] and Server[%s] properties are required.", domain, type, object, clazz, server));
            }
        }
    }
}
