package com.github.alien11689.webservices.model;

import com.github.alien11689.webservices.model.Change;
import com.github.alien11689.webservices.model.Project;
import com.github.alien11689.webservices.model.User;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ProjectTest {

    @Test
    public void shouldMarshalProjectToXml() throws JAXBException {
        Project project = new Project("Test project");
        User user = new User("admin", "admin@test.pl", "Jan", "Kowalski");
        project.setOwner(user);
        Change change = new Change();
        change.setId(15l);
        change.setNewCode("test");
        change.setOldCode("newTest");
        project.setChanges(Collections.singletonList(change));

        JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
        StringWriter sw = new StringWriter();
        jaxbContext.createMarshaller().marshal(project, sw);

        String xml = sw.toString();
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                        "<project name=\"Test project\" xmlns=\"http://github.com/alien11689/webservices/model\">" +
                        "<owner login=\"admin\" email=\"admin@test.pl\" firstName=\"Jan\" lastName=\"Kowalski\"/>" +
                        "<changes>" +
                        "<change id=\"15\"><oldCode>newTest</oldCode><newCode>test</newCode></change>" +
                        "</changes>" +
                        "</project>", xml);
    }

    @Test
    public void shouldUnMarshalXmlIntoProject() throws JAXBException, ParserConfigurationException, IOException, SAXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<project name=\"Test project\" xmlns=\"http://github.com/alien11689/webservices/model\">" +
                "<owner login=\"admin\" email=\"admin@test.pl\" firstName=\"Jan\" lastName=\"Kowalski\"/>" +
                "<changes>" +
                "<change id=\"15\"><oldCode>newTest</oldCode><newCode>test</newCode></change>" +
                "</changes>" +
                "</project>";

        JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Node node = documentBuilder
                .parse(new ByteArrayInputStream(xml.getBytes()))
                .getDocumentElement();
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Project project = (Project) unmarshaller.unmarshal(node);

        assertEquals("Test project", project.getName());
        assertEquals("admin", project.getOwner().getLogin());
        assertEquals("admin@test.pl", project.getOwner().getEmail());
        assertEquals("Jan", project.getOwner().getFirstName());
        assertEquals("Kowalski", project.getOwner().getLastName());
        assertEquals(1, project.getChanges().size());
        assertEquals(Long.valueOf(15l), project.getChanges().get(0).getId());
        assertEquals("test", project.getChanges().get(0).getNewCode());
        assertEquals("newTest", project.getChanges().get(0).getOldCode());

    }
}