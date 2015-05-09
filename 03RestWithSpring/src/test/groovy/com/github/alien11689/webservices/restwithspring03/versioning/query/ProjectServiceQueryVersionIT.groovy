package com.github.alien11689.webservices.restwithspring03.versioning.query

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Shared
import spock.lang.Specification

class ProjectServiceQueryVersionIT extends Specification {
    private WebClient createClient() {
        WebClient
                .create('http://localhost:8080/')
                .path('03RestWithSpring/api1/queryVersioning/projects')
    }

    @Shared
    Project testProject = new Project(name: "Test project")

    void setup() {
        createClient().delete()
        createClient().post(testProject)
    }

    def "should get project in two versions"() {
        expect:
            createClient().query("version", "v1").getCollection(Project)[0].name == 'TEST PROJECT'
            createClient().query("version", "v2").getCollection(Project)[0].name == 'Test project'
    }
}