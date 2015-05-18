package com.github.alien11689.webservices.restwithspring03.versioning.mediatype.parameter

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Shared
import spock.lang.Specification

class ProjectResourceMediaTypeParameterVersionIT extends Specification {
    private WebClient createClient() {
        WebClient
                .create('http://localhost:8080/', [new JacksonJaxbJsonProvider()])
                .path('03RestWithSpring/api1/mediatypeParameterVersioning/projects')
    }

    @Shared
    Project testProject = new Project(name: "Test project")

    void setup() {
        createClient().delete()
        createClient().post(testProject)
    }

    def "should get project in two versions"() {
        expect:
            createClient().accept("application/vnd.com.github.alien11689+json; version=1").getCollection(Project)[0].name == 'TEST PROJECT'
            createClient().accept("application/vnd.com.github.alien11689+json; version=2").getCollection(Project)[0].name == 'Test project'
            createClient().accept("application/vnd.com.github.alien11689+json; version=3").get().status == 406
//            createClient().accept("application/vnd.com.github.alien11689+json").getCollection(Project)[0].name == 'Test project'
    }
}