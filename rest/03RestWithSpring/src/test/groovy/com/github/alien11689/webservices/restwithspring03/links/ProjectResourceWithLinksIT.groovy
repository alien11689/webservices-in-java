package com.github.alien11689.webservices.restwithspring03.links

import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.core.Response

class ProjectResourceWithLinksIT extends Specification {

    private static final URI baseUri = new URI('http://localhost:8080/03RestWithSpring/api1/links/project')

    private static WebClient createClient(URI uri) {
        WebClient.create(uri)
    }

    void setup() {
        createClient(baseUri).delete()
    }

    @Unroll
    def "should use links to travel through application"() {
        given:
            Project project = new Project("test project")
        when:
            Response discoverGet = createClient(baseUri).get()
        then:
            discoverGet.links.size() == 3
            discoverGet.links.find { it.rel == "projects:get" }.uri == baseUri
            discoverGet.links.find { it.rel == "project:create" }.uri == baseUri
            discoverGet.links.find { it.rel == "project:create" }.type == "application/vnd.com.github.alien.project+xml"
            discoverGet.links.find { it.rel == "projects:delete" }.uri == baseUri
        when:
            Response postResponse = createClient(discoverGet.links.find { it.rel == "project:create" }.uri)
                    .type(discoverGet.links.find { it.rel == "project:create" }.type)
                    .post(project)
        then:
            postResponse.links.size() == 2
            postResponse.links.find { it.rel == "projects:get" }.uri == baseUri
            postResponse.links.find { it.rel == "project:get" }.uri == new URI("$baseUri/test%20project")
            URI newProjectUri = postResponse.links.find { it.rel == "project:get" }.uri
        when:
            Response secondPostResponse = createClient(discoverGet.links.find { it.rel == "project:create" }.uri)
                    .type(discoverGet.links.find { it.rel == "project:create" }.type)
                    .post(project)
        then:
            secondPostResponse.links.size() == 1
            secondPostResponse.links.find { it.rel == "projects:get" }.uri == baseUri
        when:
            Response getProjectResponse = createClient(newProjectUri).get()
        then:
            getProjectResponse.readEntity(Project) == project
            getProjectResponse.links.size() == 3
            getProjectResponse.links.find { it.rel == "project:get" }.uri == newProjectUri
            getProjectResponse.links.find { it.rel == "project:delete" }.uri == newProjectUri
            getProjectResponse.links.find { it.rel == "projects:get" }.uri == baseUri
        when:
            Response getAllAfterPost = createClient(baseUri).get()
        then:
            getAllAfterPost.links.size() == 4
            getAllAfterPost.links.find { it.rel == "projects:get" }.uri == baseUri
            getAllAfterPost.links.find { it.rel == "project:create" }.uri == baseUri
            getAllAfterPost.links.find { it.rel == "project:create" }.type == "application/vnd.com.github.alien.project+xml"
            getAllAfterPost.links.find { it.rel == "projects:delete" }.uri == baseUri
            getAllAfterPost.links.find { it.rel == "project:get" }.uri == new URI("$baseUri/PROJECT_NAME")
        when:
            Response deleteProjectResponse = createClient(newProjectUri).delete()
        then:
            deleteProjectResponse.links.size() == 1
            deleteProjectResponse.links.find { it.rel == "projects:get" }.uri == baseUri
        when:
            Response deleteAll = createClient(baseUri).delete()
        then:
            deleteAll.links.size() == 1
            deleteAll.links.find { it.rel == 'projects:get' }.uri == baseUri
    }
}
