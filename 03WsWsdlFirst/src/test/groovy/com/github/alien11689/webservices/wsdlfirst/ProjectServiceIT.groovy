package com.github.alien11689.webservices.wsdlfirst

import com.github.alien11689.webservices.model.Project
import com.github.alien11689.webservices.model.User
import org.apache.cxf.message.Message
import spock.lang.Specification

import javax.xml.ws.BindingProvider

class ProjectServiceIT extends Specification {

    private ProjectServicePortType client = new ProjectService(new URL("http://localhost:8080/03WsWsdlFirst/ProjectService?wsdl")).getProjectServicePort()

    void setup() {
        client.deleteAll(new DeleteAllRequest())
    }

    def "should get empty project list at the beggining"() {
        expect:
            client.getProjects(new GetProjectsRequest()).project == []
    }

    def "should create project with success"() {
        when:
            client.createProject(new CreateProjectRequest(project: new Project(name: "Test project")), new User(login: "admin", email: 'admin@test.org'))
        then:
            Project project = client.getProject(new GetProjectRequest(name: "Test project")).project
            project.getName() == "Test project"
            project.getOwner()?.getLogin() == "admin"
    }

    def "should throw fault when trying to create the same project second time"() {
        when:
            client.createProject(new CreateProjectRequest(project: new Project(name: "Test project")), new User(login: "admin", email: 'admin@test.org'))
            client.createProject(new CreateProjectRequest(project: new Project(name: "Test project")), new User(login: "admin", email: 'admin@test.org'))
        then:
            Fault_Exception createException = thrown(Fault_Exception)
            createException.faultInfo.code == BigInteger.valueOf(2)
    }

    def "should throw fault when getting not existing project"(){
        when:
            client.getProject(new GetProjectRequest(name: "Unknown")).project
        then:
            Fault_Exception getException = thrown(Fault_Exception)
            getException.faultInfo.code == BigInteger.ONE
    }

    def "should create project in tryRun mode so project will not be created"() {
        when: "dry run"
            ((BindingProvider) client).getRequestContext().put(Message.PROTOCOL_HEADERS as String, ["dryRun": ["true"]])
            client.createProject(new CreateProjectRequest(project: new Project(name: "Test project")), new User(login: "admin", email: 'admin@test.org'))
        then:
            client.getProjects(new GetProjectsRequest()).project == []
    }

    //TODO rewrite to spring
}