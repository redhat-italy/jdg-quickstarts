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
package it.redhat.playground.visualizer.service;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.logging.Logger;

@Path("/")
public class JDGRest {
    @Inject
    JDGHelper jdgHelper;

    @Inject
    private Logger log;

    @GET
    @Path("/{key}/locate")
    @Produces({ "application/json" })
    public String locate(@PathParam("key") String key) {
        return "{\"result\":\"" + jdgHelper.locate(key) + "\"}";
    }

    @GET
    @Path("/{key}")
    @Produces({ "application/json" })
    public String get(@PathParam("key") String key) {
        return "{\"result\":\"" + jdgHelper.get(key) + "\"}";
    }

    @GET
    @Path("/{key}/put/{value}")
    @Produces({ "application/json" })
    public String postWithGet(@PathParam("key") String key, @PathParam("value") String value) {
        return "{\"result\":\"" + jdgHelper.post(key, value) + "\"}";
    }

    @GET
    @Path("/{key}/pia/{value}")
    @Produces({ "application/json" })
    public String pia(@PathParam("key") String key, @PathParam("value") String value) {
        return "{\"result\":\"" + jdgHelper.pia(key, value) + "\"}";
    }

    @POST
    @Path("/{key}/{value}")
    @Produces({ "application/json" })
    public String post(@PathParam("key") String key, @PathParam("value") String value) {
        return "{\"result\":\"" + jdgHelper.post(key, value) + "\"}";
    }

    @GET
    @Path("/loadtest")
    @Produces({ "application/json" })
    public String loadtest() {
        return "{\"result\":\"" + jdgHelper.loadtest() + "\"}";
    }

    @GET
    @Path("/key")
    @Produces({ "application/json" })
    public String key() {
        return "{\"result\":\"" + jdgHelper.key() + "\"}";
    }

    @GET
    @Path("/all")
    @Produces({ "application/json" })
    public String all() {
        return "{\"result\":\"" + jdgHelper.all() + "\"}";
    }

    @GET
    @Path("/local")
    @Produces({ "application/json" })
    public String local() {
        return "{\"result\":\"" + jdgHelper.local() + "\"}";
    }

    @GET
    @Path("/primary")
    @Produces({ "application/json" })
    public String primary() {
        return "{\"result\":\"" + jdgHelper.primary() + "\"}";
    }

    @GET
    @Path("/replica")
    @Produces({ "application/json" })
    public String replica() {
        return "{\"result\":\"" + jdgHelper.replica() + "\"}";
    }

    @GET
    @Path("/address")
    @Produces({ "application/json" })
    public String address() {
        return "{\"result\":\"" + jdgHelper.address() + "\"}";
    }

    @GET
    @Path("/info")
    @Produces({ "application/json" })
    public String info() {
        return "{\"result\":\"" + jdgHelper.info() + "\"}";
    }

    @GET
    @Path("/routing")
    @Produces({ "application/json" })
    public String routing() {
        return "{\"result\":\"" + jdgHelper.routing() + "\"}";
    }

    @GET
    @Path("/hashtags")
    @Produces({ "application/json" })
    public String hashtags() {
        return "{\"result\":\"" + jdgHelper.hashtags() + "\"}";
    }

}
