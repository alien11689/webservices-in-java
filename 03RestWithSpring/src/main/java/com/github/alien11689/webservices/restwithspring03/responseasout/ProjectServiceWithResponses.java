package com.github.alien11689.webservices.restwithspring03.responseasout;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/rawResponse/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectServiceWithResponses {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Project get(@QueryParam("name") String name) {
        if(projects.containsKey(name)){
            return projects.get(name);
        }
        throw new WebApplicationException(404);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Project p) {
        if(projects.containsKey(p.getName())){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        projects.put(p.getName(), p);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(Project p) {
        if(!projects.containsKey(p.getName())){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        projects.put(p.getName(), p);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }
}
