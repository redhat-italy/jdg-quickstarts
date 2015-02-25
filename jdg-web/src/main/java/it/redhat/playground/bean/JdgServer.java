package it.redhat.playground.bean;

/**
 * Created by samuele on 2/24/15.
 */
public class JdgServer {

    private int numberOfEntries;

    private String serverName;

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }

    public String getServerName() {
        return serverName;
    }


}
