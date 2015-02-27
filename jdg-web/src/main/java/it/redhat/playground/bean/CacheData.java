package it.redhat.playground.bean;

import java.util.List;

/**
 * Created by samuele on 2/24/15.
 */
public class CacheData {

    List<JdgServer> servers;

    public List<JdgServer> getServers() {
        return servers;
    }

    public void setServers(List<JdgServer> servers) {
        this.servers = servers;
    }
}
