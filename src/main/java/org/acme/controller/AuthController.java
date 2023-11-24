package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.User;
import org.acme.request.UserCredentials;
import org.acme.service.AuthService;
import org.acme.service.UserService;
import org.acme.utils.TokenUtils;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;
    @Inject
    UserService userService;
    @POST
    @Path("/login")
    public Response login(User userCredentials) throws Exception {
        if (authenticate(userCredentials.getUsername(), userCredentials.getPassword())) {
            User user = userService.findUserByUsername(userCredentials.getUsername());
            return Response.ok(TokenUtils.generateToken(user.getUsername(), user.getRole())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String username, String password) {
        User user = userService.findUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}

