package it.redhat.playground.visualizer.service;

import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.cdi.Input;
import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;
import org.infinispan.remoting.transport.Address;

import javax.inject.Inject;

public class MapperKeysToAddress implements Mapper<Long, Value, String, Long> {

    @Inject
    @Input
    private Cache<Long, Value> cache;

    @Override
    public void map(Long key, Value value, Collector<String, Long> collector) {
        Address address = locate(key);
        collector.emit(address.toString(), 1L);
    }

    private Address locate(Long key) {
        return cache.getAdvancedCache().getDistributionManager().getConsistentHash().locatePrimaryOwner(key);
    }
}
