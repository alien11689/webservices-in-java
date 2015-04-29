package com.github.alien11689.webservices.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    @XmlAttribute(required = true)
    private String login;

    @XmlAttribute(required = true)
    private String email;

    @XmlAttribute
    private String firstName;

    @XmlAttribute
    private String lastName;

    private transient String password;

    public User() {
    }

    public User(String login, String email, String firstName, String lastName) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        return !(lastName != null ? !lastName.equals(user.lastName) : user.lastName != null);

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
