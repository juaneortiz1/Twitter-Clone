package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.StreamDTO;
import org.acme.services.StreamService;

@Path("/api/streams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StreamController {
    @Inject
    StreamService streamService;

    @POST
    public Response createStream(StreamDTO streamDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(streamService.createStream(streamDTO))
                .build();
    }

    @GET
    @Path("/{streamId}")
    public Response getStream(@PathParam("streamId") Long streamId) {
        return Response.ok(streamService.getStream(streamId)).build();
    }

    @GET
    public Response getAllStreams() {
        return Response.ok(streamService.getAllStreams()).build();
    }
}
