package com.booking.api.Service;

import com.booking.api.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String UserName;



    @Autowired
    private JavaMailSender mailSender;

    void sendVerificationEmail(User user) {
        String verificationLink = "http://localhost:8080/verify-email?email=" + user.getEmail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(UserName);
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Thank you for registering. Please click the following link to verify your email: " + verificationLink);

        mailSender.send(message);
    }
    void sendCancellationEmail(String toEmail, String className){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(UserName);
        message.setTo(toEmail);
        message.setSubject("Cancellation Email");
        message.setText("Your booking for the class '" + className + "' has been successfully cancelled.");

        mailSender.send(message);

    }
    }



