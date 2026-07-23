package com.covira.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // OTP email
    public void sendOtpEmail(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("covirateams@gmail.com");
        message.setTo(to);
        message.setSubject("Covira Password Reset OTP");

        message.setText(
                "Hello,\n\n" +
                        "Your Covira password reset verification code is:\n\n" +
                        otp +
                        "\n\nThis code will expire in 5 minutes.\n\n" +
                        "If you did not request this password reset, please ignore this email.\n\n" +
                        "Regards,\n" +
                        "Covira Team"
        );

        mailSender.send(message);
    }
    public void sendWelcomeEmail(String toEmail, String fullName) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("🎉 Welcome to Covira");

        message.setText(
                "Hello " + fullName + ",\n\n" +

                        "Welcome to Covira!\n\n" +

                        "Your employer account has been created successfully.\n\n" +

                        "You can now:\n" +
                        "• Create video interviews\n" +
                        "• Manage interview questions\n" +
                        "• Invite candidates\n" +
                        "• Review candidate responses\n\n" +

                        "Login here:\n" +
                        "http://localhost:5173/login\n\n" +

                        "Thank you for choosing Covira.\n\n" +

                        "Regards,\n" +
                        "The Covira Team"
        );

        mailSender.send(message);
    }
}