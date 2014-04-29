package it.redhat.playground.mapreduce;

import org.infinispan.distexec.mapreduce.Reducer;

import java.util.Iterator;

public class ReducerCountGroup implements Reducer<String, Long> {

    @Override
    public Long reduce(String reducedKey, Iterator<Long> iter) {
        long result = 0;
        while (iter.hasNext()) {
            result += iter.next();
        }
        return result;
    }
}
