package it.redhat.playground.service;

import it.redhat.playground.bean.CacheData;
import it.redhat.playground.bean.JdgServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuele on 2/24/15.
 */
@Path("/jdg")
public class JdgService {

    @GET
    @Path("/distinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public CacheData getDistributionInfo() {

        CacheData data = new CacheData();
        JdgServer server = new JdgServer();
        server.setNumberOfEntries(10);
        server.setServerName("jdg-1");

        List<JdgServer> serverList = new ArrayList<JdgServer>();
        serverList.add(server);
        data.setServers(serverList);

        return data;
    }

}
