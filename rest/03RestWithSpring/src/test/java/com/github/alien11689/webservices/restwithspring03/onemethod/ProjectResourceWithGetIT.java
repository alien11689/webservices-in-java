package com.github.alien11689.webservices.restwithspring03.onemethod;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ProjectResourceWithGetIT {
    @Test
    public void shouldReturnProjectNameAsXml() {
        given()
                .accept(ContentType.XML)
        .when()
                .get("/03RestWithSpring/api1/onlyGet/project")
        .then()
                .contentType(ContentType.XML)
                .body("project.@name", equalTo("Test project"));
    }

    @Test
    public void shouldReturnProjectNameAsJson() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get("/03RestWithSpring/api1/onlyGet/project")
        .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("Test project"));
    }

    @Test
    public void shouldReturnProjectNameAsJsonAfterNegotiation() {
        given()
                .accept("application/json; q=0.8, application/xml; q=0.7")
        .when()
                .get("/03RestWithSpring/api1/onlyGet/project")
        .then()
                .contentType(ContentType.JSON);
    }

    @Test
    public void shouldReturnProjectNameAsXmlAfterNegotiation() {
        given()
                .accept("application/json; q=0.5, application/xml; q=0.7")
        .when()
                .get("/03RestWithSpring/api1/onlyGet/project")
        .then()
                .contentType(ContentType.XML);
    }
}