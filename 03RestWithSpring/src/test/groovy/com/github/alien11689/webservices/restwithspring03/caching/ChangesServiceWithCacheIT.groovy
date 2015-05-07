package com.github.alien11689.webservices.restwithspring03.caching

import com.github.alien11689.webservices.model.Change
import com.github.alien11689.webservices.model.Grade
import com.github.alien11689.webservices.model.Review
import org.apache.cxf.jaxrs.client.WebClient
import spock.lang.Specification
import spock.lang.Unroll

import javax.ws.rs.core.Response
import java.time.LocalDateTime
import java.time.ZoneId


class ChangesServiceWithCacheIT extends Specification {
    WebClient client = WebClient
            .create('http://localhost:8080/')
            .path('03RestWithSpring/api1/v1/change')

    def "should use cache when getting change"() {
        LocalDateTime now = LocalDateTime.now()
        given:
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
        when:
            Response response = WebClient
                    .create('http://localhost:8080/')
                    .path('03RestWithSpring/api1/v1/change')
                    .path("/{id}", changeWithId.id).get()
        then:
            response.status == 200
            (response.readEntity(Change) as Change).reviews.size() == 2
        when:
            Response responseFromCache = WebClient
                    .create('http://localhost:8080/')
                    .path('03RestWithSpring/api1/v1/change')
                    .modified(Date.from(now.minusHours(8).atZone(ZoneId.systemDefault()).toInstant()), false)
                    .path("/{id}", changeWithId.id).head()
        then:
            responseFromCache.status == 304
        when:
            Response responseOneMoreTime = WebClient
                    .create('http://localhost:8080/')
                    .path('03RestWithSpring/api1/v1/change')
                    .modified(Date.from(now.minusDays(1).minusHours(8).atZone(ZoneId.systemDefault()).toInstant()), false)
                    .path("/{id}", changeWithId.id).head()
        then:
            responseOneMoreTime.status == 200
    }
}
