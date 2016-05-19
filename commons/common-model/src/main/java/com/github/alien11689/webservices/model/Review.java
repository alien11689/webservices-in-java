package com.github.alien11689.webservices.model;

import com.github.alien11689.webservices.model.adapters.DateAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;

@XmlType
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != null ? !id.equals(review.id) : review.id != null) return false;
        if (grade != review.grade) return false;
        if (reviewer != null ? !reviewer.equals(review.reviewer) : review.reviewer != null) return false;
        if (comments != null ? !comments.equals(review.comments) : review.comments != null) return false;
        return !(reviewDate != null ? !reviewDate.equals(review.reviewDate) : review.reviewDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (reviewer != null ? reviewer.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (reviewDate != null ? reviewDate.hashCode() : 0);
        return result;
    }
}
