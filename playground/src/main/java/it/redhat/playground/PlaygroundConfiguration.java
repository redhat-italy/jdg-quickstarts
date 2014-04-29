package it.redhat.playground;

import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.commands.*;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaygroundConfiguration {

    private final static Logger log = LoggerFactory.getLogger(PlaygroundConfiguration.class);

    protected DefaultCacheManager manager;
    protected Cache<Long, Value> cache;

    private TextUI textUI;

    public final PlaygroundConfiguration configure() {
        banner();

        GlobalConfiguration glob = new GlobalConfigurationBuilder().clusteredDefault() // Builds a default clustered
                // configuration
                .transport().addProperty("configurationFile", "jgroups-udp.xml") // provide a specific JGroups configuration
                .globalJmxStatistics().allowDuplicateDomains(true).enable() // This method enables the jmx statistics of
                        // the global configuration and allows for duplicate JMX domains
                .build(); // Builds the GlobalConfiguration object
        Configuration loc = new ConfigurationBuilder().jmxStatistics().enable() // Enable JMX statistics
                .clustering().cacheMode(CacheMode.DIST_SYNC) // Set Cache mode to DISTRIBUTED with SYNCHRONOUS replication
                .hash().numOwners(2) // Keeps two copies of each key/value pair
                        //.expiration().lifespan(ENTRY_LIFESPAN) // Set expiration - cacheManager entries expire after some time (given by
                        // the lifespan parameter) and are removed from the cacheManager (cluster-wide).
                .build();

        manager = new DefaultCacheManager(glob, loc, true);
        cache = manager.getCache();

        textUI = new TextUI(System.in, System.out);
        for (ConsoleCommand command : baseCommands()) {
            textUI.register(command);
        }
        return this;
    }

    public final void start() {
        try {
            textUI.start();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    protected List<ConsoleCommand> baseCommands() {
        return Arrays.asList(new ClearConsoleCommand(manager),
                new GetConsoleCommand(cache),
                new HelpConsoleCommand(),
                new InfoConsoleCommand(manager),
                new LoadTestConsoleCommand(cache),
                new LocalConsoleCommand(cache),
                new LocateConsoleCommand(cache),
                new PutIfAbsentConsoleCommand(cache),
                new PrimaryConsoleCommand(cache),
                new PutConsoleCommand(cache),
                new QuitConsoleCommand(manager),
                new RoutingConsoleCommand(cache));
    }

    protected void banner() {
        System.out.println("---------------------------------------");
        System.out.println("           JDG Testing CLI");
        System.out.println("            written by uL");
        System.out.println("---------------------------------------");
        System.out.println();
    }

}
