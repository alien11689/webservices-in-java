package com.github.alien11689.simplews01;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Main {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorldImpl();
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        factoryBean.setServiceClass(HelloWorld.class);
        factoryBean.setServiceBean(helloWorld);
        factoryBean.setAddress("http://localhost:8081/01");
        factoryBean.create();
    }
}
