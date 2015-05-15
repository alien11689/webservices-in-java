package com.github.alien11689.webservices.restwithspring03.exceptions.raw

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response

class ProjectResourceWithRawExceptionsIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/rawExceptions/project')

    void setup() {
        client.delete()
    }

    Project project = new Project("TestProject")

    def "should throw exception with INTERNAL_SERVER_ERROR code when project does not exist"() {
        when:
            client.query("name", project.name).get(Project)
        then:
            InternalServerErrorException e = thrown(InternalServerErrorException)
            e.response.status == 500
    }

    def "should return NO_CONTENT code when project is created"() {
        when:
            Response response = client.post(project)
        then:
            response.status == 204
    }

    def "should return INTERNAL_SERVER_ERROR code when trying to create project second time"() {
        given:
            client.post(project)
        when:
            Response response = client.post(project)
        then:
            notThrown(InternalServerErrorException)
            response.status == 500
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
