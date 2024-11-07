package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.LoginDTO;
import org.acme.DTO.UserDTO;
import org.acme.services.UserService;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    UserService userService;

    @POST
    public Response createUser(UserDTO userDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(userService.createUser(userDTO))
                .build();
    }

    @GET
    @Path("/{userId}")
    public Response getUser(@PathParam("userId") Long userId) {
        return Response.ok(userService.getUser(userId)).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginDTO loginDTO) {
        boolean authenticated = userService.authenticate(loginDTO.username, loginDTO.password);
        if (authenticated) {
            return Response.ok(userService.getUserByUsername(loginDTO.username)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid username or password")
                    .build();
        }
    }
}
