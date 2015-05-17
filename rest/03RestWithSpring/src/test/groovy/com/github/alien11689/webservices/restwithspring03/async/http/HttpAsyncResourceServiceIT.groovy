package com.github.alien11689.webservices.restwithspring03.async.http

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.ws.rs.core.Response

class HttpAsyncResourceServiceIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/httpAsync/project')

    void setup() {
        client.delete()
    }

    def "should create and get project asynchronously"() {
        given:
            Project testProject = new Project(name: "Test project")
            def conditions = new PollingConditions(timeout: 10, initialDelay: 0, factor: 1)
            Response taskResponse = null
        when:
            Response response = client.post(testProject)
        then:
            response.status == 202
            URI taskLocation = response.location
            taskLocation != null
        when:
            WebClient taskClient = WebClient.create(taskLocation)
        then:
            conditions.eventually {
                taskResponse = taskClient.get()
                assert taskResponse.getHeaderString("status") != "PENDING"
                assert taskResponse.getHeaderString("status") == "FINISHED"
                assert taskResponse.location != null
            }
        expect:
            WebClient.create(taskResponse.location).get(Project) == testProject
    }
}
