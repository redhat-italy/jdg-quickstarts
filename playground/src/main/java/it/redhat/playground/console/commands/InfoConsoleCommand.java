/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.console.commands;

import it.redhat.playground.configuration.PlaygroundConfiguration;
import it.redhat.playground.console.UI;
import it.redhat.playground.console.support.IllegalParametersException;
import org.infinispan.manager.DefaultCacheManager;

import javax.inject.Inject;
import java.util.Iterator;

public class InfoConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "info";

    private DefaultCacheManager cacheManager;

    @Inject
    public InfoConsoleCommand(PlaygroundConfiguration conf) {
        this.cacheManager = conf.getCacheManager();
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {
        console.println(buildInfo());
        return true;
    }

    private String buildInfo() {

        StringBuilder info = new StringBuilder();
        info.append("Cache Mode: ").append(cacheManager.getCache().getCacheConfiguration().clustering().cacheModeString()).append("\n");
        info.append("Cache Manager Status: ").append(cacheManager.getStatus()).append("\n");
        info.append("Cache Manager Address: ").append(cacheManager.getAddress()).append("\n");
        info.append("Coordinator address: ").append(cacheManager.getCoordinator()).append("\n");
        info.append("Is Coordinator: ").append(cacheManager.isCoordinator()).append("\n");
        info.append("Cluster Name: ").append(cacheManager.getClusterName()).append("\n");
        info.append("Cluster Size: ").append(cacheManager.getClusterSize()).append("\n");
        info.append("Member list: ").append(cacheManager.getMembers()).append("\n");
        info.append("Cache Name: ").append(cacheManager.getCache()).append("\n");
        info.append("Cache Size: ").append(cacheManager.getCache().size()).append("\n");
        info.append("Cache Status: ").append(cacheManager.getCache().getStatus()).append("\n");
        info.append("Cache Persistence:").append(cacheManager.getCache().getCacheConfiguration().persistence().toString()).append("\n");
        //info.append("Number of Owners: ").append(cacheManager.getCache().getAdvancedCache().getDistributionManager().getWriteConsistentHash()).append("\n");
        //info.append("Number of Owners: ").append(cacheManager.getCache().getAdvancedCache().getDistributionManager().getWriteConsistentHash().getNumOwners()).append("\n");
        //info.append("Number of Segments: ").append(cacheManager.getCache().getAdvancedCache().getDistributionManager().getWriteConsistentHash().getNumSegments()).append("\n");

        return info.toString();
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME);
        console.println("\t\tInformation on cache.");
    }

}
