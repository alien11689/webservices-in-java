package com.github.alien11689.webservices.restwithspring03.versioning.path;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/pathVersioning/")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServicePathVersion {

    private Map<String, Project> projects = new HashMap<>();

    @POST
    @Path("projects")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        projects.put(p.getName(), p);
    }

    @DELETE
    @Path("/projects")
    public void deleteProjects() {
        projects.clear();
    }

    @GET
    @Path("/v1/projects")
    public Collection<Project> getProjectsV1() {
        return projects.values().stream().map(p -> new Project(p.getName().toUpperCase())).collect(Collectors.toSet());
    }

    @GET
    @Path("/v2/projects")
    public Collection<Project> getProjectsV2() {
        return projects.values();
    }
}
