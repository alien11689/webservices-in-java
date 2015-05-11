package com.github.alien11689.webservices.restwithoutdi02;

import com.github.alien11689.webservices.model.Project;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ProjectServiceIT {
    @Test
    public void shouldGetProjectWithName() throws SOAPException, MalformedURLException, JAXBException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage request = messageFactory.createMessage();
        SOAPPart soapPart = request.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody soapBody = envelope.getBody();

        QName qName = new QName("http://restwithoutdi02.webservices.alien11689.github.com/", "getProject", "req");
        soapBody.addBodyElement(qName);

        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = connectionFactory.createConnection();

        URL destination = new URL("http://localhost:8080/02WsWithoutSpring/ProjectService");

        SOAPMessage response = soapConnection.call(request, destination);
        JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
        org.w3c.dom.Node node = response.getSOAPBody().getChildNodes().item(0).getChildNodes().item(0);

        Project project = (Project) jaxbContext.createUnmarshaller().unmarshal(node);
        assertEquals("WS project", project.getName());
    }
}