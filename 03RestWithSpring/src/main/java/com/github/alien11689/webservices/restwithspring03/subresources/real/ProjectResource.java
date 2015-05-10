package com.github.alien11689.webservices.restwithspring03.subresources.real;

import com.github.alien11689.webservices.model.Project;
import com.github.alien11689.webservices.restwithspring03.subresources.simple.SimpleSubresourceProjectDb;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/realSubresource/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectResource implements ApplicationContextAware{
    private final RealSubresourceProjectDb projects;
    private ApplicationContext context;

    public ProjectResource(RealSubresourceProjectDb projects) {
        this.projects = projects;
    }

    @GET
    @Path("/{name}")
    public Project getProject(@PathParam("name") String name) {
        if (!projects.containsKey(name)) {
            throw new WebApplicationException(404);
        }
        return hideChanges(projects.get(name));
    }

    private Project hideChanges(Project project) {
        Project projectWithoutChanges = new Project();
        projectWithoutChanges.setName(project.getName());
        projectWithoutChanges.setOwner(project.getOwner());
        return projectWithoutChanges;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createProject(Project p) {
        if (projects.containsKey(p.getName())) {
            return Response.status(400).build();
        }
        if(p.getChanges() != null){
            p.setChanges(null);
        }
        projects.put(p);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    public Response deleteAll() {
        projects.clear();
        return Response.noContent().build();
    }

    @Path("/{name}/change")
    public ChangeResource changes(){
        return context.getBean(ChangeResource.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
