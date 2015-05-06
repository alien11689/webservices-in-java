package com.github.alien11689.webservices.restwithspring03.responseasout

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response

class ProjectServiceWithResponsesIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/v9/project')

    void setup() {
        client.delete()
    }

    def "should return our return codes when WebApplicationException thrown"() {
        given:
            Project project = new Project("TestProject")
        when:
            client.query("name", project.name).get(Project)
        then:
            NotFoundException e = thrown(NotFoundException)
            e.response.status == 404
        when:
            Response response = client.post(project)
        then:
            response.status == 201
        when:
            response = client.post(project)
        then:
            notThrown(InternalServerErrorException)
            response.status == 400
        when:
            Response getResponse = client.get()
        then:
            getResponse.status == 200
            getResponse.readEntity(Project) == project
    }
}
