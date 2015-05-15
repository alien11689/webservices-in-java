package com.github.alien11689.webservices.restwithspring03.withinterface

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import com.github.alien11689.webservices.restwithspring03.withInterface.ProjectResourceInterface
import org.apache.cxf.jaxrs.client.JAXRSClientFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(locations = "classpath:testBeans.xml")
class ProjectResourceWithInterfaceFromSpringIT extends Specification {

    @Autowired
    ProjectResourceInterface projectResource

    void setup() {
        projectResource.deleteProjects()
    }

    def "should create and get project via interface"() {
        given:
            Project testProject = new Project(name: "Test project", owner: new User(login: "admin", email: "admin@admin.com"))
        when:
            projectResource.create(testProject)
        then:
            projectResource.get("Test project") == testProject
    }

}