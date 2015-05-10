package com.github.alien11689.webservices.restwithspring03.versioning.path

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Shared
import spock.lang.Specification

class ProjectResourcePathVersionIT extends Specification {
    private WebClient createClient() {
        WebClient
                .create('http://localhost:8080/')
                .path('03RestWithSpring/api1/pathVersioning')
    }

    @Shared
    Project testProject = new Project(name: "Test project")

    void setup() {
        createClient().path('projects').delete()
        createClient().path('projects').post(testProject)
    }

    def "should get project in two versions"() {
        expect:
            createClient().path('v1').path('projects').getCollection(Project)[0].name == 'TEST PROJECT'
            createClient().path('v2').path('projects').getCollection(Project)[0].name == 'Test project'
    }
}