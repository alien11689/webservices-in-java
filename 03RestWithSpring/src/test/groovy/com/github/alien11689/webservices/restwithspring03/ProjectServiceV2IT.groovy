package com.github.alien11689.webservices.restwithspring03

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

class ProjectServiceV2IT extends Specification {

    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/v2/project')

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