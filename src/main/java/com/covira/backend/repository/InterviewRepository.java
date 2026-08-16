package com.covira.backend.repository;

import com.covira.backend.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByEmployerEmail(String employerEmail);

    Optional<Interview> findByAccessToken(String accessToken);
}