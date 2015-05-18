package com.github.alien11689.webservices.restwithspring03.versioning.mediatype;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/mediatypeVersioning/projects")
public class ProjectResourceMediaTypeVersion {

    private Map<String, Project> projects = new HashMap<>();

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }

    @GET
    @Produces("application/vnd.com.github.alien11689.v1+json")
    public Collection<Project> getProjectsV1() {
        return projects.values().stream().map(p -> new Project(p.getName().toUpperCase())).collect(Collectors.toSet());
    }

    @GET
    @Produces("application/vnd.com.github.alien11689.v2+json")
    public Collection<Project> getProjectsV2() {
        return projects.values();
    }
}
