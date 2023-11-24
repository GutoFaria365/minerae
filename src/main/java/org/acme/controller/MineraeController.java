package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.acme.model.Metallum;
import org.acme.service.MineraeService;

import java.util.List;

@Path("/menu")
public class MineraeController {

    @Inject
    MineraeService mineraeService;

    @GET
    @Path("/list")
    public String getMenu(){

        return "Hello from list";
    }

    @GET
    @Path("/{name}")
    public Metallum getMetallum(@PathParam("name") String name) {
        return mineraeService.findMetallumByName(name);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    @RolesAllowed("USER")
    public void addMetallum(@Context SecurityContext securityContext, Metallum metallum) {
        String username = securityContext.getUserPrincipal().getName();
        System.out.println("username = " + username);
        mineraeService.addMetallum(username, metallum);
    }
    @GET
    @RolesAllowed("USER")
    public List<Metallum> getAllMetallum(@QueryParam("filter") String filter, @Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();

        List<Metallum> metallums;
        if ("all".equalsIgnoreCase(filter)) {
            metallums = mineraeService.getAllMetallumByUsername(username);
        } else {
            metallums = mineraeService.getFilteredMetallumByUsername(username, filter);
        }

        System.out.println("Filter: " + filter + ", Data: " + metallums);

        return metallums;
    }
    @GET
    @Path("/all")
    @RolesAllowed("ADMIN")
    public List<Metallum> getAllMetallumAdmin(@QueryParam("filter") String filter) {
        List<Metallum> metallums;
        if ("all".equalsIgnoreCase(filter)) {
            metallums = mineraeService.getAllMetallum();
        } else {
            metallums = mineraeService.getFilteredMetallum(filter);
        }

        System.out.println("Filter: " + filter + ", Data: " + metallums);

        return metallums;
    }
}
