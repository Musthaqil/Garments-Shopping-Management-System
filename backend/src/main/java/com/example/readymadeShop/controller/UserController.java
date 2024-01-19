package com.example.readymadeShop.controller;

// src/main/java/com/example/readymadeShop/controller/UserController.java

import com.example.readymadeShop.utils.EmailUtil;
import com.example.readymadeShop.model.User;
import com.example.readymadeShop.repository.UserRepository;
import com.example.readymadeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import org.slf4j.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailUtil emailUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        try {
            // Add validation
            Objects.requireNonNull(user, "User must not be null");

            // Generate a verification token (magic link)
            String verificationToken = UUID.randomUUID().toString();
            user.setVerificationToken(verificationToken);

            // Save user details with the verification token
            userRepository.save(user);

            // Send a verification email with the magic link
            String verificationLink = "http://localhost:3000/verify/" + verificationToken;
            emailUtil.sendVerificationEmail(user.getEmail(), "Account Verification", verificationLink);

            // Send the verification token in the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Verification-Token", verificationToken);

            return ResponseEntity.ok("User registered successfully! Check your email for verification.");
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Please try again.");
        }
    }

    @GetMapping("/verify/{verificationToken}")
    public ResponseEntity<String> verifyAccount(@PathVariable String verificationToken) {
        // Find user by verification token
        Optional<User> optionalUser = userRepository.findByVerificationToken(verificationToken);
        try{
        if (optionalUser.isPresent()) {
            // Update user status to indicate verification
            User user = optionalUser.get();
            user.setVerified(true);
            userRepository.save(user);

            return ResponseEntity.ok("Account verified successfully!");
        } else {
            logger.error("User not found for verification token: {}", verificationToken);
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }
        }catch(Exception e){
            logger.error("Error during account verification: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Please try again.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        // Validate input if needed

        User user = userService.login(email, password);

        if (user != null) {
            // Login successful
            String userName = user.getName();
            String userType = user.getUserType();
            
            if ("admin".equals(userType)) {
                return ResponseEntity.ok("{\"message\": \"Login successful for Admin\", \"userName\": \"" + userName + "\", \"userType\": \"" + userType + "\"}");
            } else {
                return ResponseEntity.ok("{\"message\": \"Login successful\", \"userName\": \"" + userName + "\", \"userType\": \"" + userType + "\"}");
            }
        } else {
            // Login failed this lines needs to be changed
            return ResponseEntity.badRequest().body("{\"error\": \"Invalid email or password\"}");
        }
    }

    // Create a class to represent the login request
    static class UserLoginRequest {
        private String email;
        private String password;

        // Getters and setters
        public String getEmail(){
            return email;
        }
        public void setEmail(String email){
            this.email=email;
        }
        
        public String getPassword(){
            return password;
        }
        public void setPassword(String password){
            this.password=password;
        }

    }

}
