package com.covira.backend.controller;

import com.covira.backend.entity.Interview;
import com.covira.backend.service.InterviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "http://localhost:5173")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // CREATE INTERVIEW
    @PostMapping
    public Interview createInterview(
            @RequestParam String employerEmail,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestBody List<Long> questionIds) {

        return interviewService.createInterview(
                title,
                description,
                employerEmail,
                questionIds
        );
    }

    // GET EMPLOYER'S INTERVIEWS
    @GetMapping
    public List<Interview> getEmployerInterviews(
            @RequestParam String employerEmail) {

        return interviewService.getEmployerInterviews(employerEmail);
    }

    // GET INTERVIEW BY ID
    @GetMapping("/{id}")
    public Interview getInterview(@PathVariable Long id) {

        return interviewService.getInterviewById(id);
    }

    // GET INTERVIEW FOR CANDIDATE USING LINK
    @GetMapping("/candidate/{token}")
    public Interview getCandidateInterview(
            @PathVariable String token) {

        return interviewService.getInterviewByToken(token);
    }

    // UPDATE INTERVIEW
    @PutMapping("/{id}")
    public Interview updateInterview(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestBody List<Long> questionIds) {

        return interviewService.updateInterview(
                id,
                title,
                description,
                questionIds
        );
    }

    // PUBLISH INTERVIEW
    @PutMapping("/{id}/publish")
    public Interview publishInterview(@PathVariable Long id) {

        return interviewService.publishInterview(id);
    }

    // DELETE INTERVIEW
    @DeleteMapping("/{id}")
    public void deleteInterview(@PathVariable Long id) {

        interviewService.deleteInterview(id);
    }
}