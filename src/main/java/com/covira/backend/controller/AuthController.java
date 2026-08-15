package com.covira.backend.controller;

import com.covira.backend.dto.ForgotPasswordRequest;
import com.covira.backend.dto.LoginRequest;
import com.covira.backend.dto.LoginResponse;
import com.covira.backend.dto.RegisterRequest;
import com.covira.backend.dto.ResetPasswordRequest;
import com.covira.backend.dto.VerifyOtpRequest;
import com.covira.backend.entity.User;
import com.covira.backend.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    authService.register(request)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {
        try {
            User user = authService.login(request);

            session.setAttribute("loggedInUserId", user.getId());

            LoginResponse response = new LoginResponse(
                    "Login successful.",
                    user.getCompanyName(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNumber()
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok("Logout successful.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    authService.forgotPassword(request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestBody VerifyOtpRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    authService.verifyOtp(request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        try {
            return ResponseEntity.ok(
                    authService.resetPassword(request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}