package com.covira.backend.repository;

import com.covira.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByInterviewIdOrderByQuestionOrderAsc(Long interviewId);

    Optional<Question> findByIdAndInterviewId(
            Long questionId,
            Long interviewId
    );
}