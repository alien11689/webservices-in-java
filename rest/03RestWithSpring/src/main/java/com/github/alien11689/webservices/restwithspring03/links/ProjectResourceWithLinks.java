package com.github.alien11689.webservices.restwithspring03.links;

import com.github.alien11689.webservices.model.Project;
import com.github.alien11689.webservices.model.Projects;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/links/project")
@Produces("application/vnd.com.github.alien.project+xml")
public class ProjectResourceWithLinks {

    private Map<String, Project> projects = new HashMap<>();

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{name}")
    public Response getProject(@PathParam("name") String name) {
        if (!projects.containsKey(name)) {
            return Response
                    .status(404)
                    .links(getAllProjectsLink())
                    .build();
        }
        return Response
                .ok(projects.get(name))
                .links(
                        getAllProjectsLink(),
                        getProjectLink(name),
                        deleteProjectLink(name)
                ).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteProject(@PathParam("name") String name) {
        if (!projects.containsKey(name)) {
            return Response
                    .status(404)
                    .links(getAllProjectsLink())
                    .build();
        }
        projects.remove(name);
        return Response
                .noContent()
                .links(getAllProjectsLink())
                .build();
    }

    @GET
    public Response getAll() {
        return Response
                .ok(new Projects(projects.values().stream().collect(Collectors.toList())))
                .links(projects.isEmpty() ?
                        new Link[]{getAllProjectsLink(), createProjectLink()} :
                        new Link[]{getAllProjectsLink(), deleteAllProjectsLink(), createProjectLink(), getProjectLink("PROJECT_NAME")})
                .build();
    }

    @POST
    @Consumes("application/vnd.com.github.alien.project+xml")
    public Response createProject(Project p) {
        if (projects.containsKey(p.getName())) {
            return Response
                    .status(400)
                    .links(getAllProjectsLink())
                    .build();
        }
        projects.put(p.getName(), p);
        return Response
                .status(Response.Status.CREATED)
                .links(getAllProjectsLink(), getProjectLink(p.getName()))
                .build();
    }

    @DELETE
    public Response deleteAll() {
        projects.clear();
        return Response
                .noContent()
                .links(getAllProjectsLink())
                .build();
    }

    private Link getAllProjectsLink() {
        return Link
                .fromUriBuilder(uriInfo
                        .getBaseUriBuilder()
                        .path(ProjectResourceWithLinks.class))
                .rel("projects:get")
                .title("get all projects")
                .build();
    }

    private Link deleteAllProjectsLink() {
        return Link
                .fromUriBuilder(uriInfo
                        .getBaseUriBuilder()
                        .path(ProjectResourceWithLinks.class))
                .rel("projects:delete")
                .title("delete all projects")
                .build();
    }

    private Link deleteProjectLink(String name) {
        return Link
                .fromUriBuilder(uriInfo
                        .getBaseUriBuilder()
                        .path(ProjectResourceWithLinks.class)
                        .path(ProjectResourceWithLinks.class, "deleteProject"))
                .rel("project:delete")
                .title("delete project with name " + name)
                .build(name);
    }

    private Link getProjectLink(String name) {
        return Link
                .fromUriBuilder(uriInfo
                        .getBaseUriBuilder()
                        .path(ProjectResourceWithLinks.class)
                        .path(ProjectResourceWithLinks.class, "getProject"))
                .rel("project:get")
                .title("get project with name " + name)
                .build(name);
    }

    private Link createProjectLink() {
        return Link
                .fromUriBuilder(uriInfo
                        .getBaseUriBuilder()
                        .path(ProjectResourceWithLinks.class))
                .rel("project:create")
                .type("application/vnd.com.github.alien.project+xml")
                .title("create new project")
                .build();
    }
}
