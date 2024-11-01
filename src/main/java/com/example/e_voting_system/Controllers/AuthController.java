package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.LoginRequestDTO;
import com.example.e_voting_system.Model.DTO.RegistrationCompletionDTO;
import com.example.e_voting_system.Model.DTO.RegistrationRequestDTO;
import com.example.e_voting_system.Model.DTO.VerificationRequestDTO;
import com.example.e_voting_system.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Step 1: User Registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequestDTO registrationRequest) {
        try {
            userService.registerUser(registrationRequest);
            return ResponseEntity.ok("Registration successful. Please check your email for the verification code.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Step 2: Email Verification
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody VerificationRequestDTO verificationRequest) {
        try {
            String token = userService.verifyUser(verificationRequest);
            return ResponseEntity.ok("Account verified. Please complete your registration. \nYour auth token is: Bearer " + token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Step 3: Complete Registration (No Authentication Required)
    @PostMapping("/complete-registration")
    public ResponseEntity<?> completeRegistration( @RequestParam("email") String email, @Valid @RequestBody RegistrationCompletionDTO completionDTO) {
        try {
            userService.completeRegistration(email, completionDTO);
            return ResponseEntity.ok("Registration completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            String token = userService.login(loginRequest);
            return ResponseEntity.ok("Bearer " + token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }
}
