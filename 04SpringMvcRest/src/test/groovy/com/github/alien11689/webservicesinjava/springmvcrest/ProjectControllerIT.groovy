package com.github.alien11689.webservicesinjava.springmvcrest

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.NotFoundException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class ProjectControllerIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/', [new JacksonJsonProvider()])
            .accept(MediaType.APPLICATION_JSON)
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .path('/04SpringMvcRest/projectResource')

    void setup() {
        client.delete()
    }

    Project project = new Project("TestProject")

    def "should throw exception with NOT_FOUND code when project does not exist"() {
        when:
            client.path("/{name}", project.name).get(Project)
        then:
            NotFoundException e = thrown(NotFoundException)
            e.response.status == 404
    }

    def "should return CREATED code when project is created"() {
        when:
            Response response = client.post(project)
        then:
            response.status == 201
    }

    def "should return BAD_REQUEST code when trying to create project second time"() {
        given:
            client.post(project)
        when:
            Response response = client.post(project)
        then:
            response.status == 400
    }

    def "should return OK code when getting existing project"() {
        given:
            client.post(project)
        when:
            Response getResponse = client.path("/{name}", project.name).get()
        then:
            getResponse.status == 200
            getResponse.readEntity(Project) == project
    }
}
