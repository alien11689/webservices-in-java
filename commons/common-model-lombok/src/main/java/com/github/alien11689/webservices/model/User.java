package com.github.alien11689.webservices.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"login", "email", "firstName", "lastName"})
public class User {
    @XmlAttribute(required = true)
    private String login;

    @XmlAttribute(required = true)
    private String email;

    @XmlAttribute
    private String firstName;

    @XmlAttribute
    private String lastName;

    private transient char[] password;

    public User(String login, String email, String firstName, String lastName) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
