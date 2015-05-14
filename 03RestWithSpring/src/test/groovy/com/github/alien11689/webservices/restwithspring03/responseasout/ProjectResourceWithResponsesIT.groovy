package com.github.alien11689.webservices.restwithspring03.responseasout

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response

class ProjectResourceWithResponsesIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/rawResponse/project')

    void setup() {
        client.delete()
    }

    Project project = new Project("TestProject")

    def "should throw exception when getting no existing raw object"(){
        when:
            client.query("name", project.name).get(Project)
        then:
            NotFoundException e = thrown(NotFoundException)
            e.response.status == 404
    }

    def "should return CREATED code in response"() {
        when:
            Response response = client.post(project)
        then:
            response.status == 201
    }

    def "should not throw exception when getting response"() {
        given:
            client.post(project)
        when:
            Response response = client.post(project)
        then:
            notThrown(InternalServerErrorException)
            response.status == 400
    }

    def "should extract entity from response"(){
        given:
            client.post(project)
        when:
            Response getResponse = client.query("name", project.name).get()
        then:
            getResponse.status == 200
            getResponse.readEntity(Project) == project
    }
}
