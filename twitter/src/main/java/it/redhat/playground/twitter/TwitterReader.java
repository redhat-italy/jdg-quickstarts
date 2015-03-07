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

import com.twitter.hbc.httpclient.BasicClient;
import it.redhat.playground.domain.SimpleValue;
import it.redhat.playground.domain.Value;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class TwitterReader implements Runnable {

    public TwitterReader(BasicClient client, BlockingQueue<String> queue, Cache<Long, Value> cache, long timeout) {
        this.client = client;
        this.queue = queue;
        this.cache = cache;
        this.timeout = timeout;
    }

    @Override
    public void run() {

        client.connect();
        for (int msgRead = 0; msgRead < 1000000; msgRead++) {

            if (client.isDone()) {
                log.debug("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                break;
            }

            String msg;
            try {
                msg = queue.poll(timeout, TimeUnit.SECONDS);

                if (msg != null) {
                    log.debug(msg);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(msg);
                    Long id = rootNode.get("id").asLong();
                    String text = rootNode.get("text").asText();
                    cache.put(id, new SimpleValue(text));
                }
            } catch (InterruptedException | IOException e) {
                log.debug(e.getMessage());
            }
        }

        client.stop();
        log.info("Read " + client.getStatsTracker().getNumMessages() + " messages");
    }

    private BasicClient client;
    private BlockingQueue<String> queue;
    private Cache<Long, Value> cache;
    private long timeout;
    private static  final Logger log = LoggerFactory.getLogger(TwitterReader.class);

}