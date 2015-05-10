package com.github.alien11689.webservices.restwithspring03.links

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.github.alien11689.webservices.model.Project
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.core.Link
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
            discoverGet.links.size() == 1
            discoverGet.links.find {it.rel == "self"}.uri == baseUri
        when:
            Response postResponse = createClient(discoverGet.links[0].uri).post(project)
        then:
            postResponse.links.size() == 2
            postResponse.links.find {it.rel == "self"}.uri == baseUri
            URI newProjectUri = postResponse.links.find { it.rel == "new project" }.uri
            newProjectUri == new URI(baseUri.toString() + '/test%20project')
        when:
            Response secondPostResponse = createClient(discoverGet.links[0].uri).post(project)
        then:
            secondPostResponse.links.size() == 1
            secondPostResponse.links.find {it.rel == "self"}.uri == baseUri
        when:
            Response getProjectResponse = createClient(newProjectUri).get()
        then:
            getProjectResponse.readEntity(Project) == project
            getProjectResponse.links.size() == 2
            getProjectResponse.links.find {it.rel == "self"}.uri == newProjectUri
            getProjectResponse.links.find {it.rel == "up"}.uri == baseUri
        when:
            Response getAllAfterPost = createClient(baseUri).get()
        then:
            getAllAfterPost.links.size() == 2
            getAllAfterPost.links.find {it.rel == "self"}.uri == baseUri
            getAllAfterPost.links.find {it.rel == "child"}.uri == new URI(baseUri.toString() + '/PROJECT_NAME')
        when:
            Response deleteProjectResponse = createClient(newProjectUri).delete()
        then:
            deleteProjectResponse.links.size() == 1
            deleteProjectResponse.links.find {it.rel == "up"}.uri == baseUri
    }
}
