package com.github.alien11689.webservices.restwithspring03;

import com.github.alien11689.webservices.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("/v6/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServiceWithExceptions {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Project get(@QueryParam("name") String name) {
        if(projects.containsKey(name)){
            return projects.get(name);
        }
        throw new RuntimeException("No project with name " + name);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        if(projects.containsKey(p.getName())){
            throw new RuntimeException("Project " + p.getName() + " already exists");
        }
        projects.put(p.getName(), p);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(Project p) {
        if(!projects.containsKey(p.getName())){
            throw new RuntimeException("Project " + p.getName() + " does not exist");
        }
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }
}
