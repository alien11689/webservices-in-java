package com.github.alien11689.webservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Projects {
    private List<Project> projects;

    public Projects(List<Project> projects) {
        this.projects = projects;
    }

    public Projects() {
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Projects projects1 = (Projects) o;

        return !(projects != null ? !projects.equals(projects1.projects) : projects1.projects != null);

    }

    @Override
    public int hashCode() {
        return projects != null ? projects.hashCode() : 0;
    }
}
