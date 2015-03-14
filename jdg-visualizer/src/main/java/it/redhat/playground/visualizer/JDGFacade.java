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

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
public class JDGFacade {
    @Inject
    JDGService jdgService;

    @GET
    @Path("/address")
    @Produces({ "application/json" })
    public String address() {
        return "{\"result\":\"" + jdgService.address() + "\"}";
    }

    @GET
    @Path("/hashtags")
    @Produces({ "application/json" })
    public String hashtags() {
        return "{\"result\":\"" + jdgService.hashtags() + "\"}";
    }

    @GET
    @Path("/get/{key}")
    @Produces({ "application/json" })
    public String get(@PathParam("key") String key) {
        return "{\"result\":\"" + jdgService.locate(key) + "\"}";
    }

    @GET
    @Path("/locate/{key}")
    @Produces({ "application/json" })
    public String locate(@PathParam("key") String key) {
        return "{\"result\":\"" + jdgService.get(key) + "\"}";
    }

    @GET
    @Path("/info")
    @Produces({ "application/json" })
    public String info() {
        return "{\"result\":\"" + jdgService.info() + "\"}";
    }

    @GET
    @Path("/loadtest")
    @Produces({ "application/json" })
    public String loadtest() {
        return "{\"result\":\"" + jdgService.loadtest() + "\"}";
    }

    @GET
    @Path("/key")
    @Produces({ "application/json" })
    public String key() {
        return "{\"result\":\"" + jdgService.key() + "\"}";
    }

    @GET
    @Path("/local")
    @Produces({ "application/json" })
    public String local() {
        return "{\"result\":\"" + jdgService.local() + "\"}";
    }

    @GET
    @Path("/all")
    @Produces({ "application/json" })
    public String all() {
        return "{\"result\":\"" + jdgService.all() + "\"}";
    }

    @GET
    @Path("/primary")
    @Produces({ "application/json" })
    public String primary() {
        return "{\"result\":\"" + jdgService.primary() + "\"}";
    }

    @GET
    @Path("/replica")
    @Produces({ "application/json" })
    public String replica() {
        return "{\"result\":\"" + jdgService.replica() + "\"}";
    }

}
