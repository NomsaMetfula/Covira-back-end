package com.covira.backend.controller;

import com.covira.backend.dto.AIQuestionRequest;
import com.covira.backend.ai.AIQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AIQuestionController {

    private final AIQuestionService aiQuestionService;

    public AIQuestionController(AIQuestionService aiQuestionService) {
        this.aiQuestionService = aiQuestionService;
    }

    @PostMapping("/generate-question")
    public ResponseEntity<?> generateQuestion(
            @RequestBody AIQuestionRequest request) {

        try {

            String question = aiQuestionService.generateQuestion(
                    request.getJobTitle(),
                    request.getCategory(),
                    request.getDifficulty()
            );

            return ResponseEntity.ok(
                    Map.of("question", question)
            );

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(
                    Map.of(
                            "error", "Failed to generate question",
                            "message", e.getMessage()
                    )
            );
        }
    }
}