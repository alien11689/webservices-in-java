package com.github.alien11689.webservices.restwithoutdi02;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

public class ProjectServiceIT {

    @Test
    public void shouldReturnProjectName() {
        when()
                .get("/02RestWithoutDI/project")
        .then()
                .body(equalTo("<project>RestProject</project>"));
    }
}