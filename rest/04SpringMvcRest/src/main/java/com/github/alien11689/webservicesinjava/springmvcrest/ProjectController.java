package com.github.alien11689.webservicesinjava.springmvcrest;

import com.github.alien11689.webservices.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/projectResource")
public class ProjectController {

    private final Map<String, Project> projects = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Collection<Project> getAll() {
        return projects.values();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Project getProject(@PathVariable("name") String name) {
        if (projects.containsKey(name)) {
            return projects.get(name);
        }
        throw new ProjectNotFound();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody Project project) {
        if (projects.containsKey(project.getName())) {
            throw new ProjectAlreadyExists();
        }
        projects.put(project.getName(), project);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        projects.clear();
    }

}
