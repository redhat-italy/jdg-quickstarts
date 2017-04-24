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
    private PlaygroundConfiguration conf;

    @Inject
    public InfoConsoleCommand(PlaygroundConfiguration conf) {
        this.conf = conf;
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
        info.append("Cache Mode: ").append(conf.getCache().getCacheConfiguration().clustering().cacheModeString()).append("\n");
        info.append("Cache Manager Status: ").append(conf.getCacheManager().getStatus()).append("\n");
        info.append("Cache Manager Address: ").append(conf.getCacheManager().getAddress()).append("\n");
        info.append("Coordinator address: ").append(conf.getCacheManager().getCoordinator()).append("\n");
        info.append("Is Coordinator: ").append(conf.getCacheManager().isCoordinator()).append("\n");
        info.append("Cluster Name: ").append(conf.getCacheManager().getClusterName()).append("\n");
        info.append("Cluster Size: ").append(conf.getCacheManager().getClusterSize()).append("\n");
        info.append("Member list: ").append(conf.getCacheManager().getMembers()).append("\n");
        info.append("Cache Name: ").append(conf.getCache()).append("\n");
        info.append("Cache Size: ").append(conf.getCache().size()).append("\n");
        info.append("Cache Status: ").append(conf.getCache().getStatus()).append("\n");
        info.append("Cache Persistence:").append(conf.getCache().getCacheConfiguration().persistence().toString()).append("\n");
        info.append("Number of Owners: ").append(conf.getCache().getAdvancedCache().getDistributionManager().getWriteConsistentHash().getNumOwners()).append("\n");
        info.append("Number of Segments: ").append(conf.getCache().getAdvancedCache().getDistributionManager().getWriteConsistentHash().getNumSegments()).append("\n");

        return info.toString();
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME);
        console.println("\t\tInformation on cache.");
    }

}
