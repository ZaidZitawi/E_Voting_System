package com.example.e_voting_system.Controllers;



import com.example.e_voting_system.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendTestEmail(@RequestParam String toEmail) {
        // Generate a test verification code or message
        String verificationCode = "TEST1234";

        try {
            emailService.sendVerificationEmail(toEmail, verificationCode);
            return "Email sent successfully to " + toEmail;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending email: " + e.getMessage();
        }
    }
}
