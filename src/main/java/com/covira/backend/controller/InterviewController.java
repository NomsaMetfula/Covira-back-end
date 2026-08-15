package com.covira.backend.controller;

import com.covira.backend.entity.Interview;
import com.covira.backend.service.InterviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(
            InterviewService interviewService
    ) {
        this.interviewService = interviewService;
    }

    /*
     * ============================================================
     * CREATE INTERVIEW
     * ============================================================
     *
     * POST:
     * /api/interviews
     *
     * Creates an interview for the logged-in employer.
     * ============================================================
     */

    @PostMapping
    public ResponseEntity<?> createInterview(
            @RequestBody Map<String, String> request,
            HttpSession session
    ) {

        Long employerId = getEmployerId(session);

        if (employerId == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {

            Interview interview =
                    interviewService.createInterview(
                            employerId,
                            request.get("title"),
                            request.get("position"),
                            request.get("department"),
                            request.get("employmentType"),
                            request.get("location"),
                            request.get("description")
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(interview);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /*
     * ============================================================
     * GET ALL INTERVIEWS
     * ============================================================
     *
     * GET:
     * /api/interviews
     *
     * Returns all interviews belonging to the logged-in employer.
     * ============================================================
     */

    @GetMapping
    public ResponseEntity<?> getAllInterviews(
            HttpSession session
    ) {

        Long employerId = getEmployerId(session);

        if (employerId == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {

            List<Interview> interviews =
                    interviewService.getAllInterviews(
                            employerId
                    );

            return ResponseEntity.ok(interviews);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /*
     * ============================================================
     * GET ONE INTERVIEW
     * ============================================================
     *
     * GET:
     * /api/interviews/{id}
     *
     * Returns one interview belonging to the logged-in employer.
     * ============================================================
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getInterview(
            @PathVariable Long id,
            HttpSession session
    ) {

        Long employerId = getEmployerId(session);

        if (employerId == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {

            Interview interview =
                    interviewService.getInterview(
                            employerId,
                            id
                    );

            return ResponseEntity.ok(interview);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    /*
     * ============================================================
     * PUBLIC INTERVIEW
     * ============================================================
     *
     * No employer login/session required.
     *
     * GET:
     * /api/interviews/public/{token}
     *
     * Allows a candidate to access an interview using its
     * public interview token.
     * ============================================================
     */

    @GetMapping("/public/{token}")
    public ResponseEntity<?> getInterviewByToken(
            @PathVariable String token
    ) {

        try {

            Interview interview =
                    interviewService.getInterviewByToken(
                            token
                    );

            return ResponseEntity.ok(interview);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    /*
     * ============================================================
     * GET LOGGED-IN EMPLOYER
     * ============================================================
     *
     * Retrieves the employer ID stored in the HTTP session.
     * ============================================================
     */

    private Long getEmployerId(
            HttpSession session
    ) {

        return (Long) session.getAttribute(
                "loggedInUserId"
        );
    }
}