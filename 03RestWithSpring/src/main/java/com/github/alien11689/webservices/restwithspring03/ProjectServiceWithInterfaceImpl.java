package com.github.alien11689.webservices.restwithspring03;

import com.github.alien11689.webservices.model.Project;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
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
