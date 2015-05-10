package com.github.alien11689.simplerest01;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/helloWorld")
public class HelloWorldResource {

    @GET
    public String hello(){
        return "Hello world";
    }
}
