package com.github.alien11689.webservices.wswithoutdi02;

import com.github.alien11689.webservices.model.Project;

import javax.jws.WebService;

@WebService
public class ProjectServiceImpl implements ProjectService {
    public Project getProject() {
        return new Project("WS project");
    }
}
