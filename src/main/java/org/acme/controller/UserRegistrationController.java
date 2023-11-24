package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.User;
import org.acme.service.UserService;

@Path("/register")
public class UserRegistrationController {

    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String registerUser(User request) {
        userService.registerUser(request.getUsername(), request.getPassword());
        return "User registered successfully";
    }
}
