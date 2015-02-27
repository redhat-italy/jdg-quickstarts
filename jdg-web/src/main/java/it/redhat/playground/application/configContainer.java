package it.redhat.playground.application;

import org.infinispan.Cache;

/**
 * Created by samuele on 2/25/15.
 */
public class ConfigContainer {

    private static ConfigContainer INSTANCE;

    private static Cache cache;

    public static Cache getCache() {
        return cache;
    }

    public static void setCache(Cache cache) {
        ConfigContainer.cache = cache;
    }


    public static ConfigContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigContainer();
        }

        return INSTANCE;
    }


}
