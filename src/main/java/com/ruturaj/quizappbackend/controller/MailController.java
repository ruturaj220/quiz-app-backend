package com.ruturaj.quizappbackend.controller;

import com.ruturaj.quizappbackend.entity.MailStructure;
import com.ruturaj.quizappbackend.service.MailService;
import com.ruturaj.quizappbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@CrossOrigin(origins = "https://ruturaj220.github.io/quiz-app-frontend")
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final UserService userService;

    public MailController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }


    @PostMapping("/send/{mailTo}")
    public String sentMail(@PathVariable String mailTo, @RequestBody MailStructure mailStructure){
        mailService.sendMail(mailTo,mailStructure);
        return "success";
    }

    private String generatedOtp;
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        generatedOtp = String.valueOf(otp);
        return generatedOtp;
    }

    @GetMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email) {
        // Generate and send OTP
        String otp = generateOTP(); // Implement this method to generate a random OTP

        // Send the OTP to the user's email
        mailService.sendOTPEmail(email, generatedOtp);

        return ResponseEntity.ok("OTP has been sent to your email.");
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        return this.userService.updatePassword(email, otp, generatedOtp, newPassword);
    }
}

