package com.github.alien11689.webservices.restwithspring03.exceptions.webapplication

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response

class ProjectResourceWithWebApplicationExceptionsIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/webApplicationException/project')

    void setup() {
        client.delete()
    }

    Project project = new Project("TestProject")

    def "should throw exception with NOT_FOUND code when project does not exist"() {
        when:
            client.query("name", project.name).get(Project)
        then:
            NotFoundException e = thrown(NotFoundException)
            e.response.status == 404
    }

    def "should return NO_CONTENT code when project is created"() {
        when:
            Response response = client.post(project)
        then:
            response.status == 204
    }

    def "should return BAD_REQUEST code when trying to create project second time"() {
        given:
            client.post(project)
        when:
            Response response = client.post(project)
        then:
            notThrown(InternalServerErrorException)
            response.status == 400
            response.getHeaderString("error") == "Project already exists"
    }

    def "should return OK code when getting existing project"() {
        given:
            client.post(project)
        when:
            Response getResponse = client.query("name", project.name).get()
        then:
            getResponse.status == 200
            getResponse.readEntity(Project) == project
    }
}
