package com.github.alien11689.webservices.projectrest;

import com.github.alien11689.webservices.projectrest.domain.Project;
import com.github.alien11689.webservices.projectrest.domain.ProjectList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/project")
public class ProjectService {

    private Set<Project> projects = new HashSet<>();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Project> get(){
        return new ArrayList<>(projects);
    }
}
