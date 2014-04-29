package it.redhat.playground.console.commands;

import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.domain.Value;
import it.redhat.playground.mapreduce.MapperCountGroup;
import it.redhat.playground.mapreduce.ReducerCountGroup;
import org.infinispan.Cache;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.distexec.DistributedExecutorService;
import org.infinispan.distexec.mapreduce.MapReduceManagerFactory;
import org.infinispan.distexec.mapreduce.MapReduceTask;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MapReduceConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "count";
    private Cache<Long, Value> cache;

    public MapReduceConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Integer limit = Integer.parseInt(args.next());

            Future<Map<String, Long>> future = new MapReduceTask<Long, Value, String, Long>(cache)
                    .mappedWith(new MapperCountGroup(limit))
                    .reducedWith(new ReducerCountGroup())
                    .executeAsynchronously();

            try {
                Map<String, Long> map = future.get();
                console.println("Count results:");
                for (String key : map.keySet()) {
                    console.println(String.format("\t%s = %d", key, map.get(key)));
                }
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: count <limit>\nValue for limit has to be a number. In example\ncount 4");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: count <limit>");
        }
        return true;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <limit>");
        console.println("\t\tReturn how many group's names are shorter and longer than the limit");
    }
}
