package com.ruturaj.quizappbackend.controller;

import com.ruturaj.quizappbackend.entity.User;
import com.ruturaj.quizappbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://ruturaj220.github.io/quiz-app-frontend")
public class LoginController {
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String Login(@RequestBody Map<String, String> credential ) {
        System.out.println("In Login");
        String username = credential.get("username");
        String password = credential.get("password");

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            if (user.isAdmin()) {
                return "admin";
            } else {
                return "user";
            }
        }

        return "invalid";
    }
}
