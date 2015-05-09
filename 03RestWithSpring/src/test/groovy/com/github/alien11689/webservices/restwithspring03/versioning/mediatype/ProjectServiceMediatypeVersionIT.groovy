package com.github.alien11689.webservices.restwithspring03.versioning.mediatype

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Shared
import spock.lang.Specification

class ProjectServiceMediatypeVersionIT extends Specification {
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
            createClient().accept("application/vnd.com.github.alien.v1+json").getCollection(Project)[0].name == 'TEST PROJECT'
            createClient().accept("application/vnd.com.github.alien.v2+json").getCollection(Project)[0].name == 'Test project'
            createClient().getCollection(Project)[0].name == 'TEST PROJECT'
    }
}