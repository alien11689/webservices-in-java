package com.github.alien11689.webservices.restwithoutdi02;

import com.github.alien11689.webservices.model.Project;

import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
public interface ProjectService {
    @WebResult(name = "project", targetNamespace = "http://github.com/alien11689/webservices/model")
    Project getProject();
}
