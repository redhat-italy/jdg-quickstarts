package it.redhat.playground.service;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import it.redhat.playground.application.ConfigContainer;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by samuele on 3/1/15.
 */
public class TwService {


    public static void start(String hashtag) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        ConfigContainer.getInstance().setTwQueue(queue);
        System.out.println("hashtag: "+hashtag);
        // Define our endpoint: By default, delimited=length is set (we need this for our processor)
        // and stall warnings are on.
        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: set up some followings and track terms
        List<String> terms = Lists.newArrayList(hashtag);
        hosebirdEndpoint.trackTerms(terms);
        
        Authentication auth = new OAuth1(ConfigContainer.getConsumerKey(), ConfigContainer.getConsumerSecret(), ConfigContainer.getAccessToken(), ConfigContainer.getAccessTokenSecret());

        //Authentication auth = new com.twitter.hbc.httpclient.auth.BasicAuth(username, password);

        // Create a new BasicClient. By default gzip is enabled.
        BasicClient client = new ClientBuilder()
                .name("sampleExampleClient")
                .hosts(Constants.STREAM_HOST)
                .endpoint(hosebirdEndpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        ConfigContainer.getInstance().setTwService(client);

        // Start reader Thread
        TwReader reader = new TwReader();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(reader);
        ConfigContainer.getInstance().setExecutor(executor);


    }

    public static void stop() {
        BasicClient service = ConfigContainer.getInstance().getTwService();
        ExecutorService executor = ConfigContainer.getInstance().getExecutor();
        if(service != null && executor != null) {
            service.stop();
            try {
                executor.awaitTermination(5,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
