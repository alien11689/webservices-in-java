package com.github.alien11689.webservices.model;

import com.github.alien11689.webservices.model.adapters.DateAdapter;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;

@XmlType
@Data
public class Review {
    @XmlAttribute
    private Long id;

    @XmlAttribute(required = true)
    private Grade grade;

    @XmlElement(required = true)
    private User reviewer;

    @XmlElementWrapper
    @XmlElement(name = "comment")
    private List<String> comments;

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDateTime reviewDate;
}
