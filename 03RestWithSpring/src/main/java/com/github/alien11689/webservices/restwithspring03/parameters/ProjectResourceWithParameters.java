package com.github.alien11689.webservices.restwithspring03.parameters;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/withParameters/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectResourceWithParameters {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    @Path("/query")
    public Project getProjectByQuery(@QueryParam("name") String name) {
        return projectByName(name);
    }

    @GET
    @Path("/path/{name}")
    public Project getProjectByPath(@PathParam("name") String name) {
        return projectByName(name);
    }

    @GET
    @Path("/header")
    public Project getProjectByHeader(@HeaderParam("name") String name) {
        return projectByName(name);
    }

    @GET
    @Path("/cookie")
    public Project getProjectByCookie(@CookieParam("name") String name) {
        return projectByName(name);
    }

    @GET
    @Path("/matrix")
    public Project getProjectByMatrix(@MatrixParam("name") String name) {
        return projectByName(name);
    }

    @POST
    @Path("/form")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createProjectForm(@FormParam("name") String name) {
        create(new Project(name));
    }

    @GET
    @Path("/bean")
    public Project getProjectByBean(@BeanParam TwoNames twoNames) {
        return projectByName(twoNames.getName1() + " " + twoNames.getName2());
    }

    @GET
    @Path("/queryWithDefault")
    public Project getProjectByQueryWithDefault(@QueryParam("name") @DefaultValue("Test project") String name) {
        return projectByName(name);
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
