package com.github.alien11689.webservices.restwithspring03.manymethods

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.core.Response
import java.util.concurrent.Future

class ProjectServiceWithMoreMethodsIT extends Specification {

    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/manyMethods/project')

    void setup() {
        client.delete()
    }

    def "should check project endpoint"() {
        expect:
            client.getCollection(Project).isEmpty()
        when:
            Project testProject = new Project(name: "Test project", owner: new User(login: "admin", email: "admin@admin.com"))
            client.post(testProject)
        then:
            client.getCollection(Project) == [testProject]
        when:
            Project updatedProject = new Project(name: "Test project", owner: new User(login: "root", email: "root@admin.com"))
            client.put(updatedProject)
        then:
            client.getCollection(Project) == [updatedProject]
        when:
            Response response = client.query("old", "Test project").query("new", "Test").async().method("PATCH").get()
        then:
            response.status == 204
            client.getCollection(Project) == [new Project(name: "Test", owner: new User(login: "root", email: "root@admin.com"))]
        when:
            client.delete()
        then:
            client.getCollection(Project).isEmpty()
    }

    def "should get head of get"() {
        expect:
            client.head().status == 200
    }

    def "should get available operations via options"() {
        expect:
            client.options().getHeaderString("Allow").split(',') as Set == ['DELETE', 'POST', 'GET', 'PUT', 'OPTIONS', 'HEAD'] as Set
    }
}