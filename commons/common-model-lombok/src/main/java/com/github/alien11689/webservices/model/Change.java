package com.github.alien11689.webservices.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Data
public class Change {
    @XmlAttribute
    private Long id;

    @XmlElement(required = true)
    private String oldCode;

    @XmlElement(required = true)
    private String newCode;

    @XmlElementWrapper
    @XmlElement(name = "review")
    private List<Review> reviews;
}
