package com.covira.backend.service;

import com.covira.backend.entity.Interview;
import com.covira.backend.entity.User;
import com.covira.backend.repository.InterviewRepository;
import com.covira.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    public InterviewService(
            InterviewRepository interviewRepository,
            UserRepository userRepository
    ) {
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
    }

    /*
     * ============================================================
     * CREATE INTERVIEW
     * ============================================================
     */

    public Interview createInterview(
            Long employerId,
            String title,
            String position,
            String department,
            String employmentType,
            String location,
            String description
    ) {

        User employer = userRepository
                .findById(employerId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employer not found."
                        )
                );

        Interview interview = new Interview();

        interview.setEmployer(employer);

        interview.setTitle(title);

        interview.setPosition(position);

        interview.setDepartment(department);

        interview.setEmploymentType(employmentType);

        interview.setLocation(location);

        interview.setDescription(description);

        /*
         * Generate unique candidate interview token.
         */

        interview.setInterviewToken(
                UUID.randomUUID().toString()
        );

        /*
         * New interviews start as Draft.
         */

        interview.setStatus("Draft");

        /*
         * Record creation time.
         */

        interview.setCreatedAt(
                LocalDateTime.now()
        );

        return interviewRepository.save(interview);
    }

    /*
     * ============================================================
     * GET ALL INTERVIEWS
     * ============================================================
     *
     * Returns all interviews belonging to the logged-in employer.
     *
     * GET:
     * /api/interviews
     *
     */

    public List<Interview> getAllInterviews(
            Long employerId
    ) {

        return interviewRepository
                .findAllByEmployerIdOrderByIdDesc(
                        employerId
                );
    }

    /*
     * ============================================================
     * GET ONE INTERVIEW
     * ============================================================
     *
     * Returns one interview belonging to the logged-in employer.
     *
     * GET:
     * /api/interviews/{id}
     *
     */

    public Interview getInterview(
            Long employerId,
            Long interviewId
    ) {

        return interviewRepository
                .findByIdAndEmployerId(
                        interviewId,
                        employerId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Interview not found."
                        )
                );
    }

    /*
     * ============================================================
     * GET INTERVIEW BY PUBLIC TOKEN
     * ============================================================
     *
     * Used by candidates.
     *
     * No employer login/session required.
     *
     * GET:
     * /api/interviews/public/{token}
     *
     */

    public Interview getInterviewByToken(
            String token
    ) {

        return interviewRepository
                .findByInterviewToken(token)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Interview link is invalid or expired."
                        )
                );
    }
}