package com.github.alien11689.webservices.restwithspring03

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import org.apache.cxf.jaxrs.client.JAXRSClientFactory
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

class ProjectServiceWithInterfaceIT extends Specification {

    ProjectServiceWithInterface projectService = JAXRSClientFactory.create("http://localhost:8080/03RestWithSpring/api1/", ProjectServiceWithInterface.class);

    void setup() {
        projectService.deleteProjects()
    }

    def "should create and get project via interface"() {
        when:
            Project testProject = new Project(name: "Test project", owner: new User(login: "admin", email: "admin@admin.com"))
            projectService.create(testProject)
        then:
            projectService.get("Test project") == testProject
    }

}