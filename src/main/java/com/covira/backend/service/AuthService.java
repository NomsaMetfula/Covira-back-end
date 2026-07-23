package com.covira.backend.service;

import com.covira.backend.dto.*;
import com.covira.backend.entity.User;
import com.covira.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       EmailService emailService,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {

        // Prevent duplicate accounts
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "An account with this email already exists."
            );
        }

        // Validate password strength
        if (!isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character."
            );
        }

        User user = new User();

        user.setCompanyName(request.getCompanyName());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        try {

            emailService.sendWelcomeEmail(
                    user.getEmail(),
                    user.getFullName()
            );

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "User registered successfully.";
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password.")
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return "Login successful.";
    }

    public String forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Email not found.")
                );

        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000));

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);

        return "OTP sent successfully.";
    }

    public String verifyOtp(VerifyOtpRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found.")
                );

        if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP.");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired.");
        }

        return "OTP verified successfully.";
    }

    public String resetPassword(ResetPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found.")
                );

        if (!isValidPassword(request.getNewPassword())) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character."
            );
        }

        // Encrypt new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Clear OTP after successful password reset
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return "Password reset successfully.";
    }

    /**
     * Password Policy:
     * - Minimum 8 characters
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one number
     * - At least one special character
     */
    private boolean isValidPassword(String password) {

        String regex =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&^#()_+=\\-]).{8,}$";

        return password.matches(regex);
    }
}