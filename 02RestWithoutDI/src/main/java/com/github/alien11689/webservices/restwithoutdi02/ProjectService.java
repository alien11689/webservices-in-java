package com.github.alien11689.webservices.restwithoutdi02;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/project")
public class ProjectService {
    @GET
    public String get() {
        return "<project>RestProject</project>";
    }
}
