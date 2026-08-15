package com.covira.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "interview_questions")
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Interview this question belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_id", nullable = false)
    @JsonBackReference
    private Interview interview;

    /*
     * Question text
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    /*
     * Video, Text, Yes/No, Multiple Choice
     */
    @Column(nullable = false)
    private String type;

    /*
     * Whether candidate must answer this question
     */
    @Column(nullable = false)
    private boolean required = true;

    /*
     * Position of question in the interview
     */
    @Column(name = "question_order", nullable = false)
    private Integer questionOrder;


    public InterviewQuestion() {
    }


    public Long getId() {
        return id;
    }


    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }


    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }
}