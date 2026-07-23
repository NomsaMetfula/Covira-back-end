package com.covira.backend.controller;

import com.covira.backend.dto.*;
import com.covira.backend.service.AuthService;
import com.covira.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @Autowired
    private EmailService emailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        try {

            return ResponseEntity.ok(authService.forgotPassword(request));

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(authService.verifyOtp(request));

    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        try {

            return ResponseEntity.ok(authService.resetPassword(request));

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }
}