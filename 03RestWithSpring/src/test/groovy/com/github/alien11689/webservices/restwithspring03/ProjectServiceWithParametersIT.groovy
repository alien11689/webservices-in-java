package com.github.alien11689.webservices.restwithspring03

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.core.Cookie

class ProjectServiceWithParametersIT extends Specification {

    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/v4/project')

    void setup() {
        client.delete()
        client.post(testProject)
        client.post(restProject)
    }

    Project testProject = new Project(name: "Test project", owner: new User(login: "admin", email: "admin@admin.com"))
    Project restProject = new Project(name: "Rest project")

    def "should get project by name in query"() {
        expect:
            client.path("/query").query("name", "Test project").get(Project) == testProject
    }

    def "should get project by name in path"() {
        expect:
            client.path("/path/{name}", "Rest project").get(Project) == restProject
    }

    def "should get project by name in header"() {
        expect:
            client.path('/header').header("name", "Rest project").get(Project) == restProject
    }

    def "should get project by name in cookie"() {
        expect:
            client.path('/cookie').cookie(new Cookie("name", "Test project")).get(Project) == testProject
    }
}