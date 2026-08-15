package com.covira.backend.config;

import com.covira.backend.entity.Candidate;
import com.covira.backend.entity.CandidateResponse;
import com.covira.backend.entity.User;
import com.covira.backend.repository.CandidateRepository;
import com.covira.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class CandidateDemoDataConfig {

    @Bean
    CommandLineRunner seedCandidateDemoData(
            UserRepository userRepository,
            CandidateRepository candidateRepository,
            @Value("${covira.demo-data.enabled:false}") boolean enabled
    ) {
        return args -> {
            if (!enabled || candidateRepository.count() > 0) {
                return;
            }

            User employer = userRepository.findAll()
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (employer == null) {
                System.out.println(
                        "Covira demo candidates were not created: register an employer account first."
                );
                return;
            }

            Candidate thabo = candidate(
                    employer,
                    "Thabo Mokoena",
                    "thabo@example.com",
                    "Software Developer",
                    "Graduate Software Developer Interview",
                    "Information Technology",
                    "Johannesburg, South Africa",
                    "Completed",
                    LocalDateTime.of(2026, 8, 3, 10, 41)
            );

            thabo.addResponse(response(
                    1,
                    "Tell us about yourself and your experience in software development.",
                    "02:34",
                    LocalDateTime.of(2026, 8, 3, 10, 32)
            ));

            thabo.addResponse(response(
                    2,
                    "What programming languages and technologies are you most comfortable with?",
                    "01:48",
                    LocalDateTime.of(2026, 8, 3, 10, 36)
            ));

            thabo.addResponse(response(
                    3,
                    "Describe a challenging software project you have worked on.",
                    "03:12",
                    LocalDateTime.of(2026, 8, 3, 10, 41)
            ));

            Candidate lerato = candidate(
                    employer,
                    "Lerato Nkosi",
                    "lerato@example.com",
                    "UI Designer",
                    "UI Designer Interview",
                    "Design",
                    "Pretoria, South Africa",
                    "Pending",
                    null
            );

            Candidate sipho = candidate(
                    employer,
                    "Sipho Dlamini",
                    "sipho@example.com",
                    "Backend Developer",
                    "Backend Developer Interview",
                    "Information Technology",
                    "Johannesburg, South Africa",
                    "Reviewed",
                    LocalDateTime.of(2026, 8, 1, 14, 26)
            );

            sipho.addResponse(response(
                    1,
                    "Explain your experience developing backend applications.",
                    "02:21",
                    LocalDateTime.of(2026, 8, 1, 14, 20)
            ));

            sipho.addResponse(response(
                    2,
                    "How do you design and secure REST APIs?",
                    "02:57",
                    LocalDateTime.of(2026, 8, 1, 14, 26)
            ));

            Candidate naledi = candidate(
                    employer,
                    "Naledi Mokoena",
                    "naledi@example.com",
                    "Frontend Developer",
                    "Frontend Developer Interview",
                    "Information Technology",
                    "Midrand, South Africa",
                    "Completed",
                    LocalDateTime.of(2026, 7, 30, 9, 19)
            );

            naledi.addResponse(response(
                    1,
                    "What frontend frameworks have you worked with?",
                    "01:54",
                    LocalDateTime.of(2026, 7, 30, 9, 14)
            ));

            naledi.addResponse(response(
                    2,
                    "How do you ensure that a website is responsive?",
                    "02:13",
                    LocalDateTime.of(2026, 7, 30, 9, 19)
            ));

            Candidate kabelo = candidate(
                    employer,
                    "Kabelo Maseko",
                    "kabelo@example.com",
                    "Full Stack Developer",
                    "Full Stack Developer Interview",
                    "Information Technology",
                    "Soweto, South Africa",
                    "Pending",
                    null
            );

            candidateRepository.saveAll(
                    List.of(thabo, lerato, sipho, naledi, kabelo)
            );

            System.out.println("Covira demo candidates created successfully.");
        };
    }

    private Candidate candidate(
            User employer,
            String name,
            String email,
            String position,
            String interview,
            String department,
            String location,
            String status,
            LocalDateTime submittedAt
    ) {
        Candidate candidate = new Candidate();

        candidate.setEmployer(employer);
        candidate.setName(name);
        candidate.setEmail(email);
        candidate.setPosition(position);
        candidate.setInterview(interview);
        candidate.setDepartment(department);
        candidate.setLocation(location);
        candidate.setStatus(status);
        candidate.setSubmittedAt(submittedAt);

        return candidate;
    }

    private CandidateResponse response(
            int questionNumber,
            String question,
            String duration,
            LocalDateTime submittedAt
    ) {
        CandidateResponse response = new CandidateResponse();

        response.setQuestionNumber(questionNumber);
        response.setQuestion(question);
        response.setDuration(duration);
        response.setSubmittedAt(submittedAt);

        // Real video storage will populate this later.
        response.setVideoUrl(null);

        return response;
    }
}
