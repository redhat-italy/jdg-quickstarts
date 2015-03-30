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
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@ApplicationPath("/rest")
@Path("/")
public class JDGRest extends Application {

    @Inject
    private JDGHelper jdgHelper;

    @Inject
    private Logger log;

    @GET
    @Path("/{key}/locate")
    @Produces({"application/json"})
    public Response locate(@PathParam("key") String key) {
        log.info("Received locate request for key: " + key);
        return Response.ok().entity(jdgHelper.locate(key)).build();
    }

    @GET
    @Path("/{key}")
    @Produces({"application/json"})
    public Response get(@PathParam("key") String key) {
        log.info("Received get request for key: " + key);
        return Response.ok().entity(jdgHelper.get(key)).build();
    }

    @GET
    @Path("/{key}/put/{value}")
    @Produces({"application/json"})
    public Response postWithGet(@PathParam("key") String key, @PathParam("value") String value) {
        log.info("Received post (GET form) request for key: " + key + " with value: " + value);
        return Response.ok().entity(jdgHelper.post(key, value)).build();
    }

    @GET
    @Path("/{key}/pia/{value}")
    @Produces({"application/json"})
    public Response pia(@PathParam("key") String key, @PathParam("value") String value) {
        log.info("Received putIfAbsent request for key: " + key + " with value: " + value);
        return Response.ok().entity(jdgHelper.pia(key, value)).build();
    }

    @POST
    @Path("/{key}/{value}")
    @Produces({"application/json"})
    public Response post(@PathParam("key") String key, @PathParam("value") String value) {
        log.info("Received post request for key: " + key + " with value: " + value);
        return Response.ok().entity(jdgHelper.post(key, value)).build();
    }

    @GET
    @Path("/loadtest")
    @Produces({"application/json"})
    public Response loadtest() {
        log.info("Received loadtest request");
        return Response.ok().entity(jdgHelper.loadtest()).build();
    }

    @GET
    @Path("/key")
    @Produces({"application/json"})
    public Response key() {
        log.info("Received key request");
        return Response.ok().entity(jdgHelper.key()).build();
    }

    @GET
    @Path("/all")
    @Produces({"application/json"})
    public Response all() {
        log.info("Received all request");
        return Response.ok().entity(jdgHelper.all()).build();
    }

    @GET
    @Path("/local")
    @Produces({"application/json"})
    public Response local() {
        log.info("Received local request");
        return Response.ok().entity(jdgHelper.local()).build();
    }

    @GET
    @Path("/primary")
    @Produces({"application/json"})
    public Response primary() {
        log.info("Received primary request");
        return Response.ok().entity(jdgHelper.primary()).build();
    }

    @GET
    @Path("/replica")
    @Produces({"application/json"})
    public Response replica() {
        log.info("Received replica request");
        return Response.ok().entity(jdgHelper.replica()).build();
    }

    @GET
    @Path("/address")
    @Produces({"application/json"})
    public Response address() {
        log.info("Received address request");
        return Response.ok().entity(jdgHelper.address()).build();
    }

    @GET
    @Path("/info")
    @Produces({"application/json"})
    public Response info() {
        log.info("Received info request");
        return Response.ok().entity(jdgHelper.info()).build();
    }

    @GET
    @Path("/routing")
    @Produces({"application/json"})
    public Response routing() {
        log.info("Received routing request");
        return Response.ok().entity(jdgHelper.routing()).build();
    }

    @GET
    @Path("/hashtags")
    @Produces({"application/json"})
    public Response hashtags() {
        log.info("Received hashtags request");
        return Response.ok().entity(jdgHelper.hashtags()).build();
    }

    @GET
    @Path("/data/all")
    @Produces({"application/json"})
    public Response dataAll() {
        log.fine("Received data/all request");
        return Response.ok().entity(jdgHelper.getJDGInfo()).build();
    }

}
