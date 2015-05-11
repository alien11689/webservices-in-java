package com.github.alien11689.webservices.wsdlfirst;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;

public class WsdlFirstCXFNonSpringServlet extends CXFNonSpringServlet{
    @Override
    protected void loadBus(ServletConfig sc) {
        super.loadBus(sc);
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        Endpoint.publish("/ProjectService", new ProjectServiceImpl());
    }
}
