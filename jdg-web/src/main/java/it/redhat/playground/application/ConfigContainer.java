package it.redhat.playground.application;

import com.twitter.hbc.httpclient.BasicClient;
import it.redhat.playground.service.TwService;
import org.infinispan.Cache;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by samuele on 2/25/15.
 */
public class ConfigContainer {

    private static ConfigContainer INSTANCE;

    private Cache cache;
    private BasicClient twService;
    private BlockingQueue<String> twQueue;
    private ExecutorService executor;
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


    public void setTwService(BasicClient tws) {
        twService = tws;
    }

    public BasicClient getTwService() {
        return twService;
    }

    public void setTwQueue(BlockingQueue<String> twQueue) {
        this.twQueue = twQueue;
    }

    public BlockingQueue<String> getTwQueue() {
        return twQueue;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
