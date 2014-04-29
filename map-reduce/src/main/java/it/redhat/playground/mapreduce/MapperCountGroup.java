package it.redhat.playground.mapreduce;

import it.redhat.playground.domain.Value;
import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;

public class MapperCountGroup implements Mapper<Long, Value, String, Long> {

    private static final String SHORTER = "shorter";
    private static final String LONGER = "longer";
    private int limit;

    public MapperCountGroup(int limit) {
        this.limit = limit;
    }

    @Override
    public void map(Long key, Value value, Collector<String, Long> collector) {
        if(value.toString().length()<limit) {
            collector.emit(SHORTER, 1l);
        } else {
            collector.emit(LONGER, 1l);
        }
    }
}
