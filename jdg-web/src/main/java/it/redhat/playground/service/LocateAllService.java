package it.redhat.playground.service;

import it.redhat.playground.JDG;
import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;

import java.util.List;

/**
 * Created by samuele on 2/26/15.
 */
public class LocateAllService implements LocationService {
    @Override
    public List<Address> locate(Cache cache, Long key) {
        return JDG.locate(cache, key);
    }
}
