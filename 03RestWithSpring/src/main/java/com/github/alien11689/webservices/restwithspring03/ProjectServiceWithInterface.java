package com.github.alien11689.webservices.restwithspring03;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/v5/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface ProjectServiceWithInterface {
    @GET
    Project get(@QueryParam("name")String name);

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    void create(Project p);

    @DELETE
    void deleteProjects();
}
