package com.covira.backend.controller;

import com.covira.backend.dto.EmployerProfileResponse;
import com.covira.backend.dto.EmployerProfileUpdateRequest;
import com.covira.backend.service.EmployerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
public class EmployerController {

    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {

        Long userId = (Long) session.getAttribute("loggedInUserId");

        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        EmployerProfileResponse response = employerService.getProfile(userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody EmployerProfileUpdateRequest request,
            HttpSession session
    ) {

        Long userId = (Long) session.getAttribute("loggedInUserId");

        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        EmployerProfileResponse response =
                employerService.updateProfile(userId, request);

        return ResponseEntity.ok(response);
    }
}