package com.github.alien11689.webservices.restwithoutdi02;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/project")
public class ProjectResource {
    @GET
    public Project get() {
        return new Project("RestProject");
    }
}
