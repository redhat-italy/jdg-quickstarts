package it.redhat.playground.service;

import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;

import java.util.List;

/**
 * Created by samuele on 2/26/15.
 */
public interface LocationService {

    public abstract List<Address> locate(Cache cache, Long key);
}
