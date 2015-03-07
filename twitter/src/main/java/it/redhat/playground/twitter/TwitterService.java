/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

public class TwitterService {

    public TwitterService(Cache<Long, Value> cache, long timeout) {
        this.cache = cache;
        this.timeout = timeout;
    }

    public void start(String hashtag) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);
        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);
        StatusesFilterEndpoint filterEndpoint = new StatusesFilterEndpoint();
        List<String> terms = Lists.newArrayList(hashtag);
        filterEndpoint.trackTerms(terms);

        String consumerKey = System.getProperty("twitter.consumerKey", "");
        String consumerSecret = System.getProperty("twitter.consumerSecret", "");
        String token = System.getProperty("twitter.token", "");
        String tokenSecret = System.getProperty("twitter.tokenSecret", "");

        Authentication auth = new OAuth1(consumerKey,consumerSecret,token,tokenSecret);
        
        client = new ClientBuilder()
                .name("JDG #" + hashtag + " client")
                .hosts(Constants.STREAM_HOST)
                .endpoint(filterEndpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        TwitterReader reader = new TwitterReader(client, queue, cache, timeout);
        executor = Executors.newSingleThreadExecutor();
        executor.execute(reader);

    }

    public void stop() {
        if(client != null && executor != null) {
            client.stop();
            executor.shutdown();
        }
    }

    public String stats() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Number of Messages:              ").append(client.getStatsTracker().getNumMessages()).append("\n");
        buffer.append("Number of Connects:              ").append(client.getStatsTracker().getNumConnects()).append("\n");
        buffer.append("Number of Disconnects:           ").append(client.getStatsTracker().getNumDisconnects()).append("\n");
        buffer.append("Number of HTTP 2xx:              ").append(client.getStatsTracker().getNum200s()).append("\n");
        buffer.append("Number of HTTP 4xx:              ").append(client.getStatsTracker().getNum400s()).append("\n");
        buffer.append("Number of HTTP 5xx:              ").append(client.getStatsTracker().getNum500s()).append("\n");
        buffer.append("Number of Client events dropped: ").append(client.getStatsTracker().getNumClientEventsDropped()).append("\n");
        buffer.append("Number of Connection Failures:   ").append(client.getStatsTracker().getNumConnectionFailures()).append("\n");
        buffer.append("Number of Dropped messages:      ").append(client.getStatsTracker().getNumMessagesDropped()).append("\n");
        return buffer.toString();
    }

    private ExecutorService executor;
    private BasicClient client;
    private Cache<Long, Value> cache;
    private long timeout;
    private static  final Logger log = LoggerFactory.getLogger(TwitterService.class);

}
