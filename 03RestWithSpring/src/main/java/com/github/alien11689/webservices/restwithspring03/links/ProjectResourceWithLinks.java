package com.github.alien11689.webservices.restwithspring03.links;

import com.github.alien11689.webservices.model.Project;
import com.github.alien11689.webservices.model.Projects;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/links/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProjectResourceWithLinks {

    private Map<String, Project> projects = new HashMap<>();

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{name}")
    public Response getProject(@PathParam("name") String name) {
        if (!projects.containsKey(name)) {
            return Response.status(404).links(createUpAllProjectsLink()).build();
        }
        return Response.ok(projects.get(name)).links(createLinksForGet(name)).build();
    }

    private Link createUpAllProjectsLink() {
        return Link.fromUriBuilder(uriInfo.getBaseUriBuilder().path(ProjectResourceWithLinks.class)).rel("up").title("all projects").build();
    }

    private Link[] createLinksForGet(String name) {
        Link selfLink = Link.fromUriBuilder(uriInfo.getRequestUriBuilder()).rel("self").title("current project").build(name);
        return new Link[]{selfLink, createUpAllProjectsLink()};
    }

    @DELETE
    @Path("/{name}")
    public Response deleteProject(@PathParam("name") String name) {
        if (!projects.containsKey(name)) {
            return Response.status(404).links(createUpAllProjectsLink()).build();
        }
        projects.remove(name);
        return Response.noContent().links(createUpAllProjectsLink()).build();
    }

    @GET
    public Response getAll() {
        return Response
                .ok(new Projects(projects.values().stream().collect(Collectors.toList())))
                .links(createLinksForGetAll())
                    .build();
    }

    private Link[] createLinksForGetAll() {
        if (projects.isEmpty()) {
            return new Link[]{createAllProjectsLink()};
        }
        Link exampleChild = Link.fromUriBuilder(uriInfo.getRequestUriBuilder().path(ProjectResourceWithLinks.class, "getProject")).rel("child").title("project (fill placeholder)").build("PROJECT_NAME");
        return new Link[]{createAllProjectsLink(), exampleChild};
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createProject(Project p) {
        if (projects.containsKey(p.getName())) {
            return Response.status(400).links(createAllProjectsLink()).build();
        }
        projects.put(p.getName(), p);
        return Response.status(Response.Status.CREATED).links(createAllProjectsLinkWith(p.getName())).build();
    }

    private Link[] createAllProjectsLinkWith(String name) {
        Link child = Link.fromUriBuilder(uriInfo.getRequestUriBuilder().path(ProjectResourceWithLinks.class, "getProject")).rel("new project").title("new project").build(name);
        return new Link[]{createAllProjectsLink(), child};
    }

    private Link createAllProjectsLink() {
        return Link.fromUriBuilder(uriInfo.getRequestUriBuilder()).rel("self").title("all projects").build();
    }

    @DELETE
    public Response deleteAll() {
        projects.clear();
        return Response.noContent().links(createAllProjectsLink()).build();
    }
}
