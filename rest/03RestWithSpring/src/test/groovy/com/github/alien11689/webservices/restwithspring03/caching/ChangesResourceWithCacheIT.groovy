package com.github.alien11689.webservices.restwithspring03.caching

import com.github.alien11689.webservices.model.Change
import com.github.alien11689.webservices.model.Grade
import com.github.alien11689.webservices.model.Review
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification

import javax.ws.rs.core.Response
import java.time.LocalDateTime
import java.time.ZoneId

class ChangesResourceWithCacheIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/caching/change')

    LocalDateTime now = LocalDateTime.now()
    String changeId = insertChange()

    String insertChange() {
        Change change = new Change(
                newCode: "test1",
                oldCode: "test2",
                reviews: [
                        new Review(grade: Grade.GOOD,
                                reviewDate: now.minusDays(2)),
                        new Review(grade: Grade.BAD,
                                reviewDate: now.minusDays(1))
                ])
        Change changeWithId = client.post(change, Change)
        changeId = changeWithId.id
    }

    def "should get change without cache" (){
        when:
            Response response = WebClient
                    .create('http://localhost:8080/')
                    .path('03RestWithSpring/api1/caching/change')
                    .path("/{id}", changeId).get()
        then:
            response.status == 200
            (response.readEntity(Change) as Change).reviews.size() == 2
    }

    def "should use cache when getting change with modified date after last review"() {
        when:
            Response responseFromCache = WebClient
                    .create('http://localhost:8080/')
                    .path('03RestWithSpring/api1/caching/change')
                    .modified(Date.from(now.minusHours(8).atZone(ZoneId.systemDefault()).toInstant()), false)
                    .path("/{id}", changeId).head()
        then:
            responseFromCache.status == 304
    }

    def "should not use cache when getting change with modified date before last review"() {
        when:
            Response responseOneMoreTime = WebClient
                    .create('http://localhost:8080/')
                    .path('03RestWithSpring/api1/caching/change')
                    .modified(Date.from(now.minusDays(1).minusHours(8).atZone(ZoneId.systemDefault()).toInstant()), false)
                    .path("/{id}", changeId).head()
        then:
            responseOneMoreTime.status == 200
    }
}
