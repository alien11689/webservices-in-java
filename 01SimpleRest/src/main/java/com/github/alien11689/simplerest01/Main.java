package com.github.alien11689.simplerest01;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class Main {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(HelloWorld.class);
        factoryBean.setResourceProvider(HelloWorld.class, new SingletonResourceProvider(helloWorld));
        factoryBean.setAddress("http://localhost:8081/01");
        factoryBean.create();
    }
}
