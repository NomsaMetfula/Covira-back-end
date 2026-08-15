package com.covira.backend.repository;

import com.covira.backend.entity.CandidateResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateResponseRepository
        extends JpaRepository<CandidateResponse, Long> {

    Optional<CandidateResponse> findByCandidateIdAndQuestionNumber(
            Long candidateId,
            Integer questionNumber
    );
}