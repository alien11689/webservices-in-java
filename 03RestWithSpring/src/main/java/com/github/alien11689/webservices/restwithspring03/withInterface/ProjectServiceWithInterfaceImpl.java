package com.github.alien11689.webservices.restwithspring03.withInterface;

import com.github.alien11689.webservices.model.Project;

import java.util.HashMap;
import java.util.Map;

public class ProjectServiceWithInterfaceImpl implements ProjectServiceWithInterface {

    private Map<String, Project> projects = new HashMap<>();

    @Override
    public Project get(String name) {
        return projects
                .values()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .get();
    }

    @Override
    public void create(Project p) {
        projects.put(p.getName(), p);
    }

    @Override
    public void deleteProjects() {
        projects.clear();
    }
}
