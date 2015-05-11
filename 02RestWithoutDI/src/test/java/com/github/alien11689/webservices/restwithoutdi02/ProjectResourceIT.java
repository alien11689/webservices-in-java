package com.github.alien11689.webservices.restwithoutdi02;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

public class ProjectResourceIT {

    @Test
    public void shouldReturnProjectName() {
        when()
                .get("/02RestWithoutDI/project")
        .then()
                .body("project.@name", equalTo("RestProject"));
    }
}