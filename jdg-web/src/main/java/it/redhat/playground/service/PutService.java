package it.redhat.playground.service;

import it.redhat.playground.application.ConfigContainer;
import it.redhat.playground.bean.JdgEntry;

import java.util.List;

/**
 * Created by samuele on 2/26/15.
 */
public class PutService {

    public static void doPut(List<JdgEntry> entries) {

        for(JdgEntry entry : entries) {
            ConfigContainer.getInstance().getCache().put(entry.getKey(),entry.getValue());
        }
    }
}
