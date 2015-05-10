package com.github.alien11689.simplerest01;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class Main {

    public static void main(String[] args) {
        HelloWorldResource helloWorldResource = new HelloWorldResource();
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(HelloWorldResource.class);
        factoryBean.setResourceProvider(HelloWorldResource.class, new SingletonResourceProvider(helloWorldResource));
        factoryBean.setAddress("http://localhost:8081/01");
        factoryBean.create();
    }
}
