package com.github.alien11689.webservices.restwithspring03

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

class ProjectServiceV3IT extends Specification {

    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/v3/project')

    void setup() {
        client.delete()
    }

    def "should get project by name"() {
        when:
            Project testProject = new Project(name: "Test project", owner: new User(login: "admin", email: "admin@admin.com"))
            client.post(testProject)
        then:
            client.query("name", "Test project").get(Project) == testProject
    }

}