package com.covira.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_responses")
public class CandidateResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    /*
     * Candidate's written or multiple-choice answer.
     */
    @Column(columnDefinition = "TEXT")
    private String answer;

    /*
     * URL/path of the candidate's recorded video.
     */
    @Column(name = "video_url")
    private String videoUrl;

    /*
     * Duration of the recorded video.
     */
    private String duration;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;


    public CandidateResponse() {
    }


    public Long getId() {
        return id;
    }


    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }


    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}