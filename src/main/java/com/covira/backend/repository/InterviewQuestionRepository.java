package com.covira.backend.repository;

import com.covira.backend.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findAllByInterviewIdOrderByQuestionOrderAsc(
            Long interviewId
    );

    Optional<InterviewQuestion> findByIdAndInterviewId(
            Long questionId,
            Long interviewId
    );
}