package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.PostDTO;
import org.acme.services.PostService;

import java.net.URI;
import java.util.List;

@Path("/api/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {
    @Inject
    PostService postService;

    @POST
    public Response createPost(PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return Response.created(URI.create("/api/posts/" + createdPost.id)).entity(createdPost).build();
    }

    @GET
    @Path("/stream/{streamId}")
    public Response getStreamPosts(@PathParam("streamId") Long streamId) {
        List<PostDTO> posts = postService.getStreamPosts(streamId);
        if (posts.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No posts found for this stream").build();
        }
        return Response.ok(posts).build();
    }
}

