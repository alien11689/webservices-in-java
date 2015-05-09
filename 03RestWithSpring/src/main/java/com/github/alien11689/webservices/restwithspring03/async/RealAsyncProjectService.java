package com.github.alien11689.webservices.restwithspring03.async;

import com.github.alien11689.webservices.model.Project;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Path("/realAsync/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class RealAsyncProjectService {

    @Context
    private UriInfo uriInfo;

    private final Map<String, Project> projects = new HashMap<>();
    private final AtomicLong taskCounter = new AtomicLong();
    private final Map<Long, TaskStatus> tasks = new HashMap<>();

    @GET
    @Path("/{name}")
    public Project getProject(@PathParam("name") String name) {
        return projects.get(name);
    }

    @GET
    @Path("task/{id}")
    public Response getTask(@PathParam("id") long id) {
        TaskStatus taskStatus = tasks.get(id);
        Response.ResponseBuilder builder = Response.ok().header("status",taskStatus.status);
        if("FINISHED".equals(taskStatus.status)){
            return builder.location(uriInfo.getBaseUriBuilder().path("/realAsync/project/{name}").build(taskStatus.project.getName())).build();
        }
        return builder.build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Project p) {
        long taskId = taskCounter.incrementAndGet();
        final TaskStatus taskStatus = new TaskStatus(taskId, p);
        tasks.put(taskId, taskStatus);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                projects.put(taskStatus.project.getName(), taskStatus.project);
                tasks.put(taskStatus.id, taskStatus.finish());
            }
        }).start();
        return Response.accepted().location(uriInfo.getRequestUriBuilder().path("/task/{id}").build(taskId)).build();
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

    private static class TaskStatus {
        final long id;
        final Project project;
        final String status;

        public TaskStatus(long id, Project project) {
            this(id, project, "PENDING");
        }

        private TaskStatus(long id, Project project, String status) {
            this.id = id;
            this.project = project;
            this.status = status;
        }

        public TaskStatus finish(){
            return new TaskStatus(id, project, "FINISHED");
        }
    }
}
