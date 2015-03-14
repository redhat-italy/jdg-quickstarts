/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.redhat.playground.visualizer;

import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.commands.InfoConsoleCommand;
import it.redhat.playground.console.commands.LoadTestConsoleCommand;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import javax.inject.Inject;

public class JDGService {

    @Inject
    private DefaultCacheManager cacheManager;

    String address() {
        return cacheManager.getCache().toString();
    }

    String get(String key) {
        return cacheManager.getCache().get(key).toString();
    }

    String hashtags() {
        return "List all registered hashtags feeding the grid";
    }

    String info() {
        try {
            new InfoConsoleCommand(cacheManager).execute(new TextUI(System.in, System.out), null);
        } catch (IllegalParametersException e) {
            e.printStackTrace();
        }
        return "Information on cache.";
    }

    String key() {
        return "Get a key which is affine to this cluster node";
    }

    String loadtest() {
        try {
            Cache<Long, Value> cache = cacheManager.getCache();
            new LoadTestConsoleCommand(cache).execute(new TextUI(System.in, System.out), null);
        } catch (IllegalParametersException e) {
            e.printStackTrace();
        }
        return "Load example values in the grid";
    }

    String local() {
        return "List all local valuesFromKeys";
    }

    String locate() {
        return "Locate an object in the grid.";
    }

    String primary() {
        return "List all local valuesFromKeys for which this node is primary.";
    }

    String put() {
        return "Put an object (id, value) in the grid.";
    }

}

