package com.github.alien11689.webservices.restwithspring03.withinterface

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import com.github.alien11689.webservices.restwithspring03.withInterface.ProjectResourceInterface
import org.apache.cxf.jaxrs.client.JAXRSClientFactory
import spock.lang.Specification

class ProjectResourceWithInterfaceIT extends Specification {

    ProjectResourceInterface projectResource = JAXRSClientFactory.create("http://localhost:8080/03RestWithSpring/api1/", ProjectResourceInterface.class);

    void setup() {
        projectResource.deleteProjects()
    }

    def "should create and get project via interface"() {
        when:
            Project testProject = new Project(name: "Test project", owner: new User(login: "admin", email: "admin@admin.com"))
            projectResource.create(testProject)
        then:
            projectResource.get("Test project") == testProject
    }

}