package it.redhat.playground.service;

import it.redhat.playground.bean.CacheData;
import it.redhat.playground.bean.JdgServer;
import org.infinispan.Cache;
import org.infinispan.remoting.transport.Address;

import java.util.*;

/**
 * Created by samuele on 2/26/15.
 */
public class CountKeyService {

    public static Map<Address,Long> count(Cache cache, LocationService locationService) {
        Map<Address, Long> keyCountMap = new HashMap<>();

        Set<Long> keys = cache.keySet();
        for(Long k : keys) {
            List<Address> addresses = locationService.locate(cache,k);
            for(Address address : addresses) {
                if (keyCountMap.containsKey(address)) {
                    keyCountMap.put(address, keyCountMap.get(address) + 1);
                } else {
                    keyCountMap.put(address, 1l);
                }
            }
        }

        return keyCountMap;
    }

    public static CacheData toDTO(Map<Address,Long> keyCountMap) {
        CacheData data = new CacheData();
        data.setServers(new ArrayList<JdgServer>());
        for(Address a : keyCountMap.keySet()) {
            JdgServer server = new JdgServer().setServerName(a.toString())
                    .setNumberOfEntries(keyCountMap.get(a));
            data.getServers().add(server);
        }
        return data;
    }
}
