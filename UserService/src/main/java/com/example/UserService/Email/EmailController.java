package com.example.UserService.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmail/{to}/{subject}/{text}")
    public String sendEmail(
            @PathVariable String to,
            @PathVariable String subject,
            @PathVariable String text) {

        emailService.sendSimpleEmail(to, subject, text);
        return "Email sent successfully!";
    }
}
