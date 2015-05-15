package com.github.alien11689.webservices.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Change change = (Change) o;

        if (id != null ? !id.equals(change.id) : change.id != null) return false;
        if (oldCode != null ? !oldCode.equals(change.oldCode) : change.oldCode != null) return false;
        if (newCode != null ? !newCode.equals(change.newCode) : change.newCode != null) return false;
        return !(reviews != null ? !reviews.equals(change.reviews) : change.reviews != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (oldCode != null ? oldCode.hashCode() : 0);
        result = 31 * result + (newCode != null ? newCode.hashCode() : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        return result;
    }
}
