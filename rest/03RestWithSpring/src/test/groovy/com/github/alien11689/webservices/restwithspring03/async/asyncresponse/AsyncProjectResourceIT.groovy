package com.github.alien11689.webservices.restwithspring03.async.asyncresponse

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

import javax.ws.rs.client.InvocationCallback
import java.util.concurrent.Future

class AsyncProjectResourceIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/async/project')

    void setup() {
        client.delete()
        client.post(testProject)
    }

    Project testProject = new Project(name: "Test project")

    def "should get project asynchronously"() {
        given:
            PollingConditions conditions = new PollingConditions(timeout: 10, initialDelay: 0, factor: 1)
        when:
            Future<Project> future = client.query("name", 'Test project').async().get(Project)
        then:
            conditions.eventually {
                assert future.isDone()
                assert future.get() == testProject
            }
    }
}
