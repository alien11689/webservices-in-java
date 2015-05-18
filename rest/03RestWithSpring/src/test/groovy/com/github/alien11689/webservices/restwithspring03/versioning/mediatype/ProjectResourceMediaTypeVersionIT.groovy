package com.github.alien11689.webservices.restwithspring03.versioning.mediatype

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.core.Response

class ProjectResourceMediaTypeVersionIT extends Specification {
    private WebClient createClient() {
        WebClient
                .create('http://localhost:8080/', [new JacksonJaxbJsonProvider()])
                .path('03RestWithSpring/api1/mediatypeVersioning/projects')
    }

    @Shared
    Project testProject = new Project(name: "Test project")

    void setup() {
        createClient().delete()
        createClient().post(testProject)
    }

    def "should get project in two versions"() {
        expect:
            createClient().accept("application/vnd.com.github.alien11689.v1+json").getCollection(Project)[0].name == 'TEST PROJECT'
            createClient().accept("application/vnd.com.github.alien11689.v2+json").getCollection(Project)[0].name == 'Test project'
//            createClient().getCollection(Project)[0].name == 'Test project'
//            createClient().getCollection(Project)[0].name == 'TEST PROJECT'
        when:
            Response response = createClient().accept("application/vnd.com.github.alien11689.v3+json").get()
        then:
            response.status == 406
    }
}