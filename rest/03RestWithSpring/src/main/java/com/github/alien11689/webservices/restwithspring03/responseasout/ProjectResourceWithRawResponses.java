package com.github.alien11689.webservices.restwithspring03.responseasout;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/rawResponse/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectResourceWithRawResponses {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Response get(@QueryParam("name") String name) {
        if(projects.containsKey(name)){
            return Response.ok(projects.get(name)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Project p) {
        if(projects.containsKey(p.getName())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        projects.put(p.getName(), p);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(Project p) {
        if(!projects.containsKey(p.getName())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        projects.put(p.getName(), p);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    public Response deleteProjects() {
        projects.clear();
        return Response.noContent().build();
    }
}
