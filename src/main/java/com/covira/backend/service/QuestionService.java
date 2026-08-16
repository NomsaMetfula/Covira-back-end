package com.covira.backend.service;

import com.covira.backend.entity.Question;
import com.covira.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // CREATE
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    // READ - get questions belonging to a specific user
    public List<Question> getQuestionsByUserEmail(String userEmail) {
        return questionRepository.findByUserEmail(userEmail);
    }

    // READ - get one question
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Question not found"));
    }

    // UPDATE
    public Question updateQuestion(Long id, Question updatedQuestion) {

        Question question = getQuestionById(id);

        question.setQuestionText(updatedQuestion.getQuestionText());
        question.setCategory(updatedQuestion.getCategory());
        question.setDifficulty(updatedQuestion.getDifficulty());
        question.setResponseDuration(updatedQuestion.getResponseDuration());
        question.setCreationMethod(updatedQuestion.getCreationMethod());

        return questionRepository.save(question);
    }

    // DELETE
    public void deleteQuestion(Long id) {
        Question question = getQuestionById(id);
        questionRepository.delete(question);
    }
}