package it.redhat.playground.application;

import it.redhat.playground.service.TwService;

import javax.annotation.Resource;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samuele on 2/24/15.
 */
public class ApplicationConfig extends Application {

    private static final Set<Class<?>> CLASSES;

    static {
        HashSet<Class<?>> tmp = new HashSet<Class<?>>();
        tmp.add(Resource.class);

        CLASSES = Collections.unmodifiableSet(tmp);
    }

    @Override
    public Set<Class<?>> getClasses(){

        return  CLASSES;
    }


}
