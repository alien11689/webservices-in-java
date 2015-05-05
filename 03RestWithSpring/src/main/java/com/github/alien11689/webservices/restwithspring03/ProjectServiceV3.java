package com.github.alien11689.webservices.restwithspring03;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

@Path("/v3/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServiceV3 {

    @Context
    private UriInfo uriInfo;

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Project get() {
        String name = uriInfo.getQueryParameters().getFirst("name");
        return projects.values().stream().filter(p ->             p.getName().equals(name)
        ).findFirst().get();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        if (projects.containsKey(p.getName())) {
            throw new RuntimeException("Project already exists");
        }
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }
}
