package com.github.alien11689.webservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projects {
    private List<Project> projects;
}
