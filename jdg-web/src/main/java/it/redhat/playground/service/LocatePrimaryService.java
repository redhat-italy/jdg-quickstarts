package it.redhat.playground.service;

import it.redhat.playground.JDG;
import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuele on 2/26/15.
 */
public class LocatePrimaryService implements LocationService {


    @Override
    public List<Address> locate(Cache cache, Long key) {
        List<Address> addr = new ArrayList<Address>();
        addr.add(JDG.locatePrimary(cache,key));
        return addr;
    }
}
