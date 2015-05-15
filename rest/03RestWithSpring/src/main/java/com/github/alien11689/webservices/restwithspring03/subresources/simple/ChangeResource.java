package com.github.alien11689.webservices.restwithspring03.subresources.simple;

import com.github.alien11689.webservices.model.Change;
import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/simpleSubresource/project/{projectName}/change")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ChangeResource {

    private final AtomicLong changeIdCounter = new AtomicLong();

    private final SimpleSubresourceProjectDb projects;

    public ChangeResource(SimpleSubresourceProjectDb projects) {
        this.projects = projects;
    }

    @GET
    @Path("/{id}")
    public Change getChange(@PathParam("projectName") String projectName, @PathParam("id") long changeId) {
        if (!projects.containsKey(projectName)) {
            throw new WebApplicationException(404);
        }
        Project project = projects.get(projectName);
        Optional<Change> maybeChange = project.getChanges().stream().filter(c -> c.getId() == changeId).findFirst();
        return maybeChange.orElseThrow(() -> new WebApplicationException(404));
    }

    @POST
    public Change createChange(@PathParam("projectName") String projectName, Change newChange) {
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
