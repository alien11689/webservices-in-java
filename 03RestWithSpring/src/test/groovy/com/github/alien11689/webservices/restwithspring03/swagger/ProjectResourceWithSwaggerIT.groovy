package com.github.alien11689.webservices.restwithspring03.swagger

import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification
import spock.lang.Unroll

class ProjectResourceWithSwaggerIT extends Specification {

    def "should expose api via swagger ui"() {
        expect:
            WebClient.create('http://localhost:8080/03RestWithSpring/apidocs/').get().status == 200
    }

    def "should expose api via swagger"() {
        expect:
            WebClient.create('http://localhost:8080/03RestWithSpring/api1/api-docs').get().status == 200
    }
}
