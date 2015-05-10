package com.github.alien11689.webservices.restwithspring03.subresources.real

import com.github.alien11689.webservices.model.Change
import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

class ProjectWithChangeRealSubresourceIT extends Specification {
    private static WebClient createBaseClient() {
        WebClient.create('http://localhost:8080/')
                .path('03RestWithSpring/api1/realSubresource/project')
    }

    void setup() {
        createBaseClient().delete()
    }

    def "should create project and change"() {
        given:
            Project project = new Project(name: "Test project")
            Change change = new Change(oldCode: "old...", newCode: "new...")
        when:
            createBaseClient().post(project)
        then:
            createBaseClient().path(project.getName()).get(Project) == project
        when:
            Change changeWithId = createBaseClient().path(project.getName()).path("change").post(change, Change)
        then:
            createBaseClient().path(project.getName()).path("change").path(changeWithId.id).get(Change) == changeWithId
    }
}
