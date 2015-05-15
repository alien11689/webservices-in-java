package com.github.alien11689.webservices.wsdlfirst;

import com.github.alien11689.webservices.model.Project;
import com.github.alien11689.webservices.model.User;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService(
        targetNamespace = "http://alien11689.github.com/webservices/wsdlfirst",
        name = "ProjectServicePortType",
        serviceName = "ProjectService",
        portName = "ProjectServicePort")
@XmlSeeAlso({ObjectFactory.class, com.github.alien11689.webservices.model.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ProjectServiceImpl implements ProjectServicePortType {

    @Resource
    private WebServiceContext webServiceContext;


    private HashMap<String, Project> projects = new HashMap<>();

    @Override
    public GetProjectResponse getProject(@WebParam(partName = "parameters", name = "getProjectRequest", targetNamespace = "http://alien11689.github.com/webservices/wsdlfirst") GetProjectRequest parameters) throws Fault_Exception {
        if (!projects.containsKey(parameters.getName())) {
            Fault fault = new Fault();
            fault.setCode(BigInteger.ONE);
            fault.setMessage("Project does not exist");
            throw new Fault_Exception("Project does not exist", fault);
        }
        GetProjectResponse response = new GetProjectResponse();
        response.setProject(projects.get(parameters.getName()));
        return response;
    }

    @Override
    public GetProjectsResponse getProjects(@WebParam(partName = "parameters", name = "getProjectsRequest", targetNamespace = "http://alien11689.github.com/webservices/wsdlfirst") GetProjectsRequest parameters) {
        GetProjectsResponse response = new GetProjectsResponse();
        response.getProject().addAll(projects.values());
        return response;
    }

    @Override
    public CreateProjectResponse createProject(@WebParam(partName = "parameters", name = "createProjectRequest", targetNamespace = "http://alien11689.github.com/webservices/wsdlfirst") CreateProjectRequest parameters, @WebParam(partName = "credits", name = "user", targetNamespace = "http://github.com/alien11689/webservices/model", header = true) User credits) throws Fault_Exception {
        if (projects.containsKey(parameters.getProject().getName())) {
            Fault fault = new Fault();
            fault.setCode(BigInteger.valueOf(2));
            fault.setMessage("Project already exists");
            throw new Fault_Exception("Project already exists", fault);
        }
        Map<String, List<String>> httpHeaders = (Map<String, List<String>>) webServiceContext.getMessageContext().get(MessageContext.HTTP_REQUEST_HEADERS);

        boolean dryRun = httpHeaders.getOrDefault("dryRun", new ArrayList<>())
                .stream()
                .findFirst()
                .map(Boolean::valueOf)
                .orElse(false);

        if (!dryRun) {
            if (parameters.getProject().getOwner() == null) {
                parameters.getProject().setOwner(credits);
            }
            projects.put(parameters.getProject().getName(), parameters.getProject());
        }
        return new CreateProjectResponse();
    }

    @Override
    public void deleteAll(@WebParam(partName = "parameters", name = "deleteAllRequest", targetNamespace = "http://alien11689.github.com/webservices/wsdlfirst") DeleteAllRequest parameters) {
        projects.clear();
    }
}
