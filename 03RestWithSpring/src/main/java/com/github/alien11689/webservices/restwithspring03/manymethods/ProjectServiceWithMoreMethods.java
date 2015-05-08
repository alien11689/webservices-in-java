package com.github.alien11689.webservices.restwithspring03.manymethods;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("/manyMethods/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServiceWithMoreMethods {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Collection<Project> get() {
        return projects.values();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        projects.put(p.getName(), p);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(Project p) {
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }
}
