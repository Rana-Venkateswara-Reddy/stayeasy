package com.stayeasy.controller;

import com.stayeasy.model.User;
import com.stayeasy.service.EmailService;
import com.stayeasy.service.OtpService;
import com.stayeasy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://darling-semolina-22e159.netlify.app/", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final OtpService otpService;
    private final EmailService emailService;

    public AuthController(OtpService otpService, EmailService emailService, UserService userService) {
        this.otpService = otpService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        try {
            // Check if email already exists
            if (userService.getUserByEmail(email).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Email already registered"));
            }

            String otp = otpService.generateOtp(email);
            emailService.sendOtpEmail(email, otp);
            return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Failed to send OTP"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user, @RequestParam String otp) {
        try {
            // Validate OTP
            if (!otpService.validateOtp(user.getEmail(), otp)) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid OTP"));
            }

            // Register user
            User registeredUser = userService.registerUser(user);
            otpService.clearOtp(user.getEmail());

            return ResponseEntity.ok(Map.of(
                    "message", "Registration successful",
                    "user", registeredUser
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Registration failed"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (!userService.verifyUser(email, password)) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        Optional<User> userOptional = userService.getUserByEmail(email);
        User user = userOptional.get();

        // Create response user without password
        User responseUser = new User();
        responseUser.setId(user.getId());
        responseUser.setName(user.getName());
        responseUser.setEmail(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "user", responseUser,
                "message", "Login successful"
        ));
    }
}