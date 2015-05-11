package com.github.alien11689.webservices.wsdlfirst;

import com.github.alien11689.webservices.model.Project;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(
        targetNamespace = "http://alien11689.github.com/webservices/wsdlfirst",
        name = "ProjectServicePortType",
        serviceName ="ProjectService",
        portName = "ProjectServicePort")
@XmlSeeAlso({ObjectFactory.class, com.github.alien11689.webservices.model.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ProjectServiceImpl implements ProjectServicePortType {
    @Override
    public GetProjectResponse getProject(@WebParam(partName = "parameters", name = "getProjectRequest", targetNamespace = "http://alien11689.github.com/webservices/wsdlfirst") GetProjectRequest parameters) {
        Project project = new Project();
        project.setName("Test service - wsdl first");
        GetProjectResponse response = new GetProjectResponse();
        response.setProject(project);
        return response;
    }
}
