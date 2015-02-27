package it.redhat.playground.bean;

/**
 * Created by samuele on 2/24/15.
 */
public class JdgServer {

    private Long numberOfEntries;

    private String serverName;

    public JdgServer setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public Long getNumberOfEntries() {
        return numberOfEntries;
    }

    public JdgServer setNumberOfEntries(Long numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
        return this;
    }

    public String getServerName() {
        return serverName;
    }


}
