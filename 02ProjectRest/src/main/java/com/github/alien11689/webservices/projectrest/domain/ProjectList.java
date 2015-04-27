package com.github.alien11689.webservices.projectrest.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class ProjectList {
    private List<Project> projects;

    public ProjectList() {
    }

    public ProjectList(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
