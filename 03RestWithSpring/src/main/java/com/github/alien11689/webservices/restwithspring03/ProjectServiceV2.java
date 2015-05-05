package com.github.alien11689.webservices.restwithspring03;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("/v2/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServiceV2 {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Collection<Project> get() {
        return projects.values();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        if(projects.containsKey(p.getName())){
            throw new RuntimeException("Project already exists");
        }
        projects.put(p.getName(), p);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(Project p) {
        if(!projects.containsKey(p.getName())){
            throw new RuntimeException("Project does not exist");
        }
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }
}
