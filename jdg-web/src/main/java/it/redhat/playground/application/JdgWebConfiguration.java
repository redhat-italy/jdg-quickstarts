package it.redhat.playground.application;

import it.redhat.playground.configuration.PlaygroundConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by samuele on 2/25/15.
 */
public class JdgWebConfiguration extends PlaygroundConfiguration {


    public void saveCache() {
        ConfigContainer.getInstance().setCache(this.cache);
    }

    public void configTwitter() {
        String path = System.getProperty("twitter.config.file");
        if(path != null) {
            try {
                InputStream is = new FileInputStream(path);
                Properties props = new Properties();
                props.load(is);

                ConfigContainer.setConsumerKey(props.getProperty("consumer.key"));
                ConfigContainer.setConsumerSecret(props.getProperty("consumer.secret"));
                ConfigContainer.setAccessToken(props.getProperty("access.token"));
                ConfigContainer.setAccessTokenSecret(props.getProperty("access.token.secret"));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
