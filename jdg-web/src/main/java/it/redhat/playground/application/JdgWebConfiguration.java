package it.redhat.playground.application;

import it.redhat.playground.configuration.PlaygroundConfiguration;

/**
 * Created by samuele on 2/25/15.
 */
public class JdgWebConfiguration extends PlaygroundConfiguration {


    public void saveCache() {
        ConfigContainer.setCache(this.cache);
    }

}
