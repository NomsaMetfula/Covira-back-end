package com.covira.backend.repository;

import com.covira.backend.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

    List<Interview> findAllByEmployerIdOrderByIdDesc(
            Long employerId
    );

    Optional<Interview> findByIdAndEmployerId(
            Long id,
            Long employerId
    );

    Optional<Interview> findByInterviewToken(
            String interviewToken
    );

    Optional<Interview> findByTitleAndEmployerId(
            String title,
            Long employerId
    );
}