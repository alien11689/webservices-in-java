package com.github.alien11689.webservices.restwithspring03.async.longrunning

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

import javax.ws.rs.client.InvocationCallback
import java.util.concurrent.Future

class AsyncClientProjectResourceIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/async/longRunning/project')

    void setup() {
        client.delete()
        client.post(testProject)
    }

    @Shared
    Project testProject = new Project(name: "Test project")
    @Shared
    Project dummyProject = new Project(name: "Dummy project")

    @Unroll
    def "should get project asynchronously: #test"() {
        given:
            Project result = null
            InvocationCallback<Project> callback = new InvocationCallback<Project>() {
                @Override
                void completed(Project responseProject) {
                    result = responseProject
                }

                @Override
                void failed(Throwable throwable) {
                    result = dummyProject
                }
            }
            PollingConditions conditions = new PollingConditions(timeout: 10, initialDelay: 0, factor: 1)
        when:
            Future future = client.query("name", name).get(callback)
        then:
            conditions.eventually {
                assert future.isDone()
                assert result == desiredProject
            }
        where:
            name           | desiredProject | test
            "Not exist"    | dummyProject   | "not existing project"
            "Test project" | testProject    | "existing project"
    }
}
