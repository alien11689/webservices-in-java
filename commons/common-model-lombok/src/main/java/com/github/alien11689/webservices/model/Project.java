package com.github.alien11689.webservices.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Data
@NoArgsConstructor
public class Project {
    @XmlAttribute(required = true)
    private String name;

    private User owner;

    @XmlElementWrapper
    @XmlElement(name = "change")
    private List<Change> changes;

    public Project(String name) {
        this.name = name;
    }

}
