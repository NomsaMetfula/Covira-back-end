package com.covira.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id", nullable = false)
    @JsonIgnore
    private User employer;


    @Column(nullable = false)
    private String title;


    private String position;


    private String department;


    @Column(name = "employment_type")
    private String employmentType;


    private String location;


    private String description;


    @Column(
            name = "interview_token",
            nullable = false,
            unique = true
    )
    private String interviewToken;


    @Column(nullable = false)
    private String status = "Draft";


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    /*
     * ============================================================
     * QUESTIONS
     * ============================================================
     *
     * One interview can have many questions.
     *
     * This uses the same Question entity that is currently being
     * managed by QuestionController and QuestionService.
     */

    @OneToMany(
            mappedBy = "interview",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("questionOrder ASC")
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();


    public Interview() {
    }


    public Long getId() {
        return id;
    }


    public User getEmployer() {
        return employer;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getInterviewToken() {
        return interviewToken;
    }

    public void setInterviewToken(String interviewToken) {
        this.interviewToken = interviewToken;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public void addQuestion(Question question) {

        questions.add(question);

        question.setInterview(this);
    }


    public void removeQuestion(Question question) {

        questions.remove(question);

        question.setInterview(null);
    }
}