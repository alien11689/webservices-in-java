package com.github.alien11689.webservices.restwithspring03.exceptions.raw

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class ProjectServiceWithExceptionsIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/rawExceptions/project')

    void setup() {
        client.delete()
    }

    def "should throw exception and return 500 if error"() {
        given:
            Project project = new Project("TestProject")
        when:
            client.query("name", project.name).get(Project)
        then:
            InternalServerErrorException e = thrown(InternalServerErrorException)
            e.response.status == 500
        when:
            Response response = client.post(project)
        then:
            response.status == 204
        when:
            response = client.post(project)
        then:
            notThrown(InternalServerErrorException)
            response.status == 500
        when:
            Response getResponse = client.get()
        then:
            getResponse.status == 200
            getResponse.readEntity(Project) == project
    }
}
