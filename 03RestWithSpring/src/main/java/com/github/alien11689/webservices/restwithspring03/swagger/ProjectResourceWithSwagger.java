package com.github.alien11689.webservices.restwithspring03.swagger;

import com.github.alien11689.webservices.model.Project;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/swaggerAnnotated/project")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Api(value = "/swaggerAnnotated/project", description = "Project resource with swagger")
public class ProjectResourceWithSwagger {

    private Map<String, Project> projects = new HashMap<>();

    @GET
    @ApiOperation(value="Finds project by name", response = Project.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Project does not exist")
    })
    public Project get(@ApiParam(value = "name of project", required = true) @QueryParam("name") String name) {
        if (projects.containsKey(name)) {
            return projects.get(name);
        }
        throw new WebApplicationException(404);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value="Creates new project")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Project already exists")
    })
    public Response create(@ApiParam(value = "Project to create", required = true) Project p) {
        if (projects.containsKey(p.getName())) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        projects.put(p.getName(), p);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @ApiOperation(value="Deletes all projects")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Projects are deleted")
    })
    public void deleteProjects() {
        projects.clear();
    }
}
