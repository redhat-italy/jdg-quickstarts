package it.redhat.playground.service;

import com.twitter.hbc.httpclient.BasicClient;
import it.redhat.playground.application.ConfigContainer;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by samuele on 3/4/15.
 */
public class TwReader implements Runnable {

    @Override
    public void run() {
        BasicClient client = ConfigContainer.getInstance().getTwService();
        BlockingQueue<String> queue = ConfigContainer.getInstance().getTwQueue();
        client.connect();


        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 1000000; msgRead++) {
            if (client.isDone()) {
                System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                break;
            }

            String msg = null;
            try {
                msg = queue.poll(5, TimeUnit.SECONDS);

                if (msg == null) {
                    System.out.println("Did not receive a message in 5 seconds");
                } else {
                    System.out.println(msg);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(msg);
                    Long id = rootNode.get("id").asLong();
                    String text = rootNode.get("text").asText();
                    ConfigContainer.getInstance().getCache().put(id,text);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        client.stop();

        // Print some stats
        System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
    }
}
