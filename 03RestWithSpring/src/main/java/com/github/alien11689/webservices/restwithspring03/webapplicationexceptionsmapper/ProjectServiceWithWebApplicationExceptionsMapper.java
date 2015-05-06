package com.github.alien11689.webservices.restwithspring03.webapplicationexceptionsmapper;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/v8/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServiceWithWebApplicationExceptionsMapper {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Project get(@QueryParam("name") String name) {
        if(projects.containsKey(name)){
            return projects.get(name);
        }
        throw new ProjectNotFoundException();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        if(projects.containsKey(p.getName())){
            throw new ProjectAlreadyExistsException();
        }
        projects.put(p.getName(), p);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(Project p) {
        if(!projects.containsKey(p.getName())){
            throw new ProjectDoesNotExistException();
        }
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }
}
