package it.redhat.playground.visualizer.service;

import org.infinispan.distexec.mapreduce.Reducer;

import java.util.Iterator;

public class ReducerKeysToAddress implements Reducer<String, Long> {

    @Override
    public Long reduce(String address, Iterator<Long> iterator) {
        long counter = 0L;
        while(iterator.hasNext()) {
            counter += iterator.next();
        }
        return counter;
    }
}
