package com.github.alien11689.webservices.restwithspring03.async.simple;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/async/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class AsyncProjectResource {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    public void get(@QueryParam("name") String name, @Suspended final AsyncResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Project result = projectByName(name);
                    Thread.sleep(3000);
                    response.resume(result);
                } catch (Exception e) {
                    response.resume(e);
                }
            }
        }).start();
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
        return projects
                .values()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .get();
    }
}
