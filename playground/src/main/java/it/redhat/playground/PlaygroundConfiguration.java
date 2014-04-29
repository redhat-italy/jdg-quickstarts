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
import java.util.Arrays;
import java.util.List;

public class PlaygroundConfiguration {

    private final static Logger log = LoggerFactory.getLogger(PlaygroundConfiguration.class);

    protected DefaultCacheManager manager;
    protected Cache<Long, Value> cache;

    private TextUI textUI;

    public final PlaygroundConfiguration configure() {
        banner();

        GlobalConfiguration glob = new GlobalConfigurationBuilder().clusteredDefault()
                .transport().addProperty("configurationFile", "jgroups-udp.xml")
                .globalJmxStatistics().allowDuplicateDomains(true).enable()
                .build();
        Configuration loc = new ConfigurationBuilder().jmxStatistics().enable()
                .clustering().cacheMode(getCacheMode())
                .hash().numOwners(getNumOwners())
                        //.expiration().lifespan(ENTRY_LIFESPAN)
                .build();

        manager = new DefaultCacheManager(glob, loc, true);
        cache = manager.getCache();

        textUI = new TextUI(System.in, System.out);
        for (ConsoleCommand command : baseCommands()) {
            textUI.register(command);
        }
        return this;
    }

    private CacheMode getCacheMode() {
        try {
            return CacheMode.valueOf(System.getProperty("playground.cacheMode", ""));
        } catch (IllegalArgumentException e) {
            return CacheMode.DIST_SYNC;
        }
    }

    private int getNumOwners() {
        return Integer.valueOf(System.getProperty("playground.numOwners", "2"));
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
        System.out.println("           JDG Playground CLI");
        System.out.println("---------------------------------------");
        System.out.println();
    }

}
