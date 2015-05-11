package com.github.alien11689.simplews01;

import javax.jws.WebService;

@WebService(endpointInterface = "com.github.alien11689.simplews01.HelloWorld", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    @Override
    public String getMessage() {
        return "Hello world";
    }
}
