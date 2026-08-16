package com.covira.backend.controller;

import com.covira.backend.entity.Question;
import com.covira.backend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:5173")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // CREATE
    @PostMapping
    public Question createQuestion(@RequestBody Question question) {

        if (question.getCreationMethod() == null) {
            question.setCreationMethod("MANUAL");
        }

        return questionService.createQuestion(question);
    }

    // READ - get questions for a specific user
    @GetMapping
    public List<Question> getQuestionsByUserEmail(
            @RequestParam String email) {

        return questionService.getQuestionsByUserEmail(email);
    }

    // READ - get one question
    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Question updateQuestion(
            @PathVariable Long id,
            @RequestBody Question question) {

        return questionService.updateQuestion(id, question);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
}