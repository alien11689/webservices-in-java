package com.github.alien11689.webservices.projectrest.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Project {
    private String name;

    public Project(String name) {
        this.name = name;
    }

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        return !(name != null ? !name.equals(project.name) : project.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
