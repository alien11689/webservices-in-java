package com.github.alien11689.webservices.restwithspring03.async.longrunning;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/async/longRunning/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class LongRunningProjectResource {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public Project get(@QueryParam("name") String name) {
        Project result = projectByName(name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (result == null) {
            throw new WebApplicationException(404);
        }
        return result;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project p) {
        projects.put(p.getName(), p);
    }

    @DELETE
    public void deleteProjects() {
        projects.clear();
    }

    private Project projectByName(String name) {
        return projects.get(name);
    }
}
