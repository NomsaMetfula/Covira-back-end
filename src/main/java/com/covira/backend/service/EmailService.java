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
}