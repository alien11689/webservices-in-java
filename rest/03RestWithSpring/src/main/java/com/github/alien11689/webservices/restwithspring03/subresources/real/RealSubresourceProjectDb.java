package com.github.alien11689.webservices.restwithspring03.subresources.real;

import com.github.alien11689.webservices.model.Project;

import java.util.HashMap;
import java.util.Map;

public class RealSubresourceProjectDb {
    private Map<String, Project> projects = new HashMap<>();

    public Project get(String name) {
        return projects.get(name);
    }

    public boolean containsKey(String name) {
        return projects.containsKey(name);
    }

    public void clear() {
        projects.clear();
    }

    public void put(Project p) {
        projects.put(p.getName(), p);
    }
}
