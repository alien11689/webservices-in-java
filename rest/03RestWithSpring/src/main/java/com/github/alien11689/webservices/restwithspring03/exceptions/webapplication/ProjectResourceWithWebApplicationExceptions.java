package com.github.alien11689.webservices.restwithspring03.exceptions.webapplication;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/webApplicationException/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectResourceWithWebApplicationExceptions {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Project get(@QueryParam("name") String name) {
        if(projects.containsKey(name)){
            return projects.get(name);
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        if(projects.containsKey(p.getName())){
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).header("error", "Project already exists").build());
        }
        projects.put(p.getName(), p);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(Project p) {
        if(!projects.containsKey(p.getName())){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }
}
