package it.redhat.playground.application;

import org.infinispan.Cache;

/**
 * Created by samuele on 2/25/15.
 */
public class ConfigContainer {

    private static ConfigContainer INSTANCE;

    private Cache cache;

    private static String consumerKey;
    private static String consumerSecret;
    private static String accessToken;
    private static String accessTokenSecret;

    public static void setConsumerKey(String consumerKey) {
        ConfigContainer.consumerKey = consumerKey;
    }

    public static String getConsumerKey() {
        return consumerKey;
    }

    public static void setConsumerSecret(String consumerSecret) {
        ConfigContainer.consumerSecret = consumerSecret;
    }

    public static String getConsumerSecret() {
        return consumerSecret;
    }

    public static void setAccessToken(String accessToken) {
        ConfigContainer.accessToken = accessToken;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessTokenSecret(String accessTokenSecret) {
        ConfigContainer.accessTokenSecret = accessTokenSecret;
    }

    public static String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache c) {
        cache = c;
    }


    public static ConfigContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigContainer();
        }

        return INSTANCE;
    }



}
