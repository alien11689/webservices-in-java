package com.github.alien11689.webservices.restwithspring03;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/project")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProjectService {
    @GET
    public Project get() {
        return new Project("Test project");
    }
}
