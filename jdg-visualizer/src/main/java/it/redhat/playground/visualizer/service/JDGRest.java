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
import javax.ws.rs.core.Application;
import java.util.Random;
import java.util.logging.Logger;

@ApplicationPath("/rest")
@Path("/")
public class JDGRest extends Application {

    @Inject
    JDGHelper jdgHelper;

    @Inject
    private Logger log;

    @GET
    @Path("/{key}/locate")
    @Produces({"application/json"})
    public String locate(@PathParam("key") String key) {
        log.info("Received locate request for key: " + key);
        return "{\"result\":\"" + jdgHelper.locate(key) + "\"}";
    }

    @GET
    @Path("/{key}")
    @Produces({"application/json"})
    public String get(@PathParam("key") String key) {
        log.info("Received get request for key: " + key);
        return "{\"result\":\"" + jdgHelper.get(key) + "\"}";
    }

    @GET
    @Path("/{key}/put/{value}")
    @Produces({"application/json"})
    public String postWithGet(@PathParam("key") String key, @PathParam("value") String value) {
        log.info("Received post (GET form) request for key: " + key + " with value: " + value);
        return "{\"result\":\"" + jdgHelper.post(key, value) + "\"}";
    }

    @GET
    @Path("/{key}/pia/{value}")
    @Produces({"application/json"})
    public String pia(@PathParam("key") String key, @PathParam("value") String value) {
        log.info("Received putIfAbsent request for key: " + key + " with value: " + value);
        return "{\"result\":\"" + jdgHelper.pia(key, value) + "\"}";
    }

    @POST
    @Path("/{key}/{value}")
    @Produces({"application/json"})
    public String post(@PathParam("key") String key, @PathParam("value") String value) {
        log.info("Received post request for key: " + key + " with value: " + value);
        return "{\"result\":\"" + jdgHelper.post(key, value) + "\"}";
    }

    @GET
    @Path("/loadtest")
    @Produces({"application/json"})
    public String loadtest() {
        log.info("Received loadtest request");
        return "{\"result\":\"" + jdgHelper.loadtest() + "\"}";
    }

    @GET
    @Path("/key")
    @Produces({"application/json"})
    public String key() {
        log.info("Received key request");
        return "{\"result\":\"" + jdgHelper.key() + "\"}";
    }

    @GET
    @Path("/all")
    @Produces({"application/json"})
    public String all() {
        log.info("Received all request");
        return "{\"result\":\"" + jdgHelper.all() + "\"}";
    }

    @GET
    @Path("/local")
    @Produces({"application/json"})
    public String local() {
        log.info("Received local request");
        return "{\"result\":\"" + jdgHelper.local() + "\"}";
    }

    @GET
    @Path("/primary")
    @Produces({"application/json"})
    public String primary() {
        log.info("Received primary request");
        return "{\"result\":\"" + jdgHelper.primary() + "\"}";
    }

    @GET
    @Path("/replica")
    @Produces({"application/json"})
    public String replica() {
        log.info("Received replica request");
        return "{\"result\":\"" + jdgHelper.replica() + "\"}";
    }

    @GET
    @Path("/address")
    @Produces({"application/json"})
    public String address() {
        log.info("Received address request");
        return "{\"result\":\"" + jdgHelper.address() + "\"}";
    }

    @GET
    @Path("/info")
    @Produces({"application/json"})
    public String info() {
        log.info("Received info request");
        return "{\"result\":\"" + jdgHelper.info() + "\"}";
    }

    @GET
    @Path("/routing")
    @Produces({"application/json"})
    public String routing() {
        log.info("Received routing request");
        return "{\"result\":\"" + jdgHelper.routing() + "\"}";
    }

    @GET
    @Path("/hashtags")
    @Produces({"application/json"})
    public String hashtags() {
        log.info("Received hashtags request");
        return "{\"result\":\"" + jdgHelper.hashtags() + "\"}";
    }

    @GET
    @Path("/data/all")
    @Produces({"application/json"})
    public String dataAll() {
        log.info("Received data/all request");
        int v1 = new Random().nextInt(30) + 10;
        int v2 = new Random().nextInt(40) + 10;
        int v3 = 100 - v1 - v2;
        //String result = "[['data1'," + v1 + "],['data2'," + v2 + "],['data3'," + v3 + "]]";
        String result = "data1,data2,data3\n" + v1 + "," + v2 + "," + v3 + "\n";

        log.info("Result is " + result);
        //return "{\"result\":\"" + result + "\"}";
        return result;

    }

}
