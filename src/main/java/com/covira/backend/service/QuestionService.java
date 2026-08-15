package com.covira.backend.service;

import com.covira.backend.entity.Interview;
import com.covira.backend.entity.Question;
import com.covira.backend.repository.InterviewRepository;
import com.covira.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            InterviewRepository interviewRepository
    ) {
        this.questionRepository = questionRepository;
        this.interviewRepository = interviewRepository;
    }

    /*
     * ============================================================
     * CREATE QUESTION
     * ============================================================
     */

    public Question createQuestion(
            Long interviewId,
            String questionText,
            String questionType,
            Integer timeLimit
    ) {

        Interview interview = interviewRepository
                .findById(interviewId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Interview not found."
                        )
                );

        List<Question> existingQuestions =
                questionRepository
                        .findAllByInterviewIdOrderByQuestionOrderAsc(
                                interviewId
                        );

        Question question = new Question();

        question.setInterview(interview);
        question.setQuestionText(questionText);
        question.setQuestionType(questionType);
        question.setTimeLimit(timeLimit);

        question.setQuestionOrder(
                existingQuestions.size() + 1
        );

        return questionRepository.save(question);
    }

    /*
     * ============================================================
     * GET QUESTIONS
     * ============================================================
     */

    public List<Question> getQuestions(Long interviewId) {

        return questionRepository
                .findAllByInterviewIdOrderByQuestionOrderAsc(
                        interviewId
                );
    }

    /*
     * ============================================================
     * GET PUBLIC QUESTIONS
     * ============================================================
     */

    public List<Question> getPublicQuestions(String token) {

        Interview interview =
                interviewRepository
                        .findByInterviewToken(token)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Interview link is invalid or expired."
                                )
                        );

        return questionRepository
                .findAllByInterviewIdOrderByQuestionOrderAsc(
                        interview.getId()
                );
    }

    /*
     * ============================================================
     * GET ONE QUESTION
     * ============================================================
     */

    public Question getQuestion(
            Long interviewId,
            Long questionId
    ) {

        return questionRepository
                .findByIdAndInterviewId(
                        questionId,
                        interviewId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Question not found."
                        )
                );
    }

    /*
     * ============================================================
     * DELETE QUESTION
     * ============================================================
     */

    public void deleteQuestion(
            Long interviewId,
            Long questionId
    ) {

        Question question = getQuestion(
                interviewId,
                questionId
        );

        questionRepository.delete(question);
    }
}