package com.covira.backend.service;

import com.covira.backend.entity.Interview;
import com.covira.backend.entity.Question;
import com.covira.backend.repository.InterviewRepository;
import com.covira.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final QuestionRepository questionRepository;

    public InterviewService(
            InterviewRepository interviewRepository,
            QuestionRepository questionRepository) {

        this.interviewRepository = interviewRepository;
        this.questionRepository = questionRepository;
    }

    // CREATE INTERVIEW
    public Interview createInterview(
            String title,
            String description,
            String employerEmail,
            List<Long> questionIds) {

        Interview interview = new Interview();

        interview.setTitle(title);
        interview.setDescription(description);
        interview.setEmployerEmail(employerEmail);

        // Generate unique candidate link token
        interview.setAccessToken(UUID.randomUUID().toString());

        // New interviews start as DRAFT
        interview.setStatus("DRAFT");

        interview.setCreatedAt(LocalDateTime.now());

        // Add selected questions
        if (questionIds != null && !questionIds.isEmpty()) {

            List<Question> questions =
                    questionRepository.findAllById(questionIds);

            interview.setQuestions(questions);
        }

        return interviewRepository.save(interview);
    }

    // GET ALL INTERVIEWS FOR EMPLOYER
    public List<Interview> getEmployerInterviews(String employerEmail) {

        return interviewRepository.findByEmployerEmail(employerEmail);
    }

    // GET ONE INTERVIEW
    public Interview getInterviewById(Long id) {

        return interviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));
    }

    // GET INTERVIEW USING CANDIDATE LINK
    public Interview getInterviewByToken(String token) {

        return interviewRepository.findByAccessToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));
    }

    // UPDATE INTERVIEW
    public Interview updateInterview(
            Long id,
            String title,
            String description,
            List<Long> questionIds) {

        Interview interview = getInterviewById(id);

        interview.setTitle(title);
        interview.setDescription(description);

        if (questionIds != null) {

            List<Question> questions =
                    questionRepository.findAllById(questionIds);

            interview.setQuestions(questions);
        }

        return interviewRepository.save(interview);
    }

    // PUBLISH INTERVIEW
    public Interview publishInterview(Long id) {

        Interview interview = getInterviewById(id);

        if (interview.getQuestions() == null ||
                interview.getQuestions().isEmpty()) {

            throw new IllegalArgumentException(
                    "An interview must contain at least one question."
            );
        }

        interview.setStatus("PUBLISHED");

        return interviewRepository.save(interview);
    }

    // DELETE INTERVIEW
    public void deleteInterview(Long id) {

        Interview interview = getInterviewById(id);

        interviewRepository.delete(interview);
    }
}