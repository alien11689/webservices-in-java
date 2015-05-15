package com.github.alien11689.webservices.restwithspring03.subresources.real;

import com.github.alien11689.webservices.model.Change;
import com.github.alien11689.webservices.model.Project;
import com.github.alien11689.webservices.restwithspring03.subresources.simple.SimpleSubresourceProjectDb;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ChangeResource {

    private final AtomicLong changeIdCounter = new AtomicLong();

    private final RealSubresourceProjectDb projects;

    public ChangeResource(RealSubresourceProjectDb projects) {
        this.projects = projects;
    }

    @GET
    @Path("/{id}")
    public Change getChange(@PathParam("name") String projectName, @PathParam("id") long changeId) {
        if (!projects.containsKey(projectName)) {
            throw new WebApplicationException(404);
        }
        Project project = projects.get(projectName);
        Optional<Change> maybeChange = project.getChanges().stream().filter(c -> c.getId() == changeId).findFirst();
        return maybeChange.orElseThrow(() -> new WebApplicationException(404));
    }

    @POST
    public Change createChange(@PathParam("name") String projectName, Change newChange) {
        if (!projects.containsKey(projectName)) {
            throw new WebApplicationException(404);
        }
        Project project = projects.get(projectName);
        newChange.setId(changeIdCounter.incrementAndGet());
        if (project.getChanges() == null){
            project.setChanges(Arrays.asList(newChange));
        }else{
            project.getChanges().add(newChange);
        }
        return newChange;
    }
}
