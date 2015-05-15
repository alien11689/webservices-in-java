package com.github.alien11689.webservices.restwithspring03.parameters

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Cookie
import javax.ws.rs.core.Form
import javax.ws.rs.core.MultivaluedHashMap

class ProjectResourceWithParametersIT extends Specification {

    WebClient client = createClient()

    private WebClient createClient() {
        WebClient
                .create('http://localhost:8080/')
                .path('03RestWithSpring/api1/withParameters/project')
    }

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

    def "should get project with number as name"() {
        expect:
            client.path("/pathNumber/{id}", "42").get(Project) == new Project("42")
        when:
            client.path("/pathNumber/{id}", "test").get(Project)
        then:
            thrown(WebApplicationException)
    }

    def "should get project by name in header"() {
        expect:
            client.path('/header').header("name", "Rest project").get(Project) == restProject
    }

    def "should get project by name in cookie"() {
        expect:
            client.path('/cookie').cookie(new Cookie("name", "Test project")).get(Project) == testProject
    }

    def "should create new project by name in form param and get with matrix"() {
        when:
            createClient().path('/form').form(new Form(new MultivaluedHashMap<String, String>(['name': "1234"])))
        then:
            createClient().path('/matrix').matrix("name", "1234").get(Project) == new Project(name: "1234")
    }

    def "should get project by name in bean"() {
        expect:
            client.path("/bean").query("name1", "Test").query("name2", "project").get(Project) == testProject
    }

    def "should get project by name as default param"() {
        expect:
            client.path("/queryWithDefault").get(Project) == testProject
    }
}