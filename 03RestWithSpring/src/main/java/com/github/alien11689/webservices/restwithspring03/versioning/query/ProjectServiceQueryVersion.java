package com.github.alien11689.webservices.restwithspring03.versioning.query;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/queryVersioning/projects")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServiceQueryVersion {

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
    public Collection<Project> getProjectsV1(@QueryParam("version") String version) {
        if ("v1".equals(version)) {
            return projects.values().stream().map(p -> new Project(p.getName().toUpperCase())).collect(Collectors.toSet());
        }
        return projects.values();
    }

}
