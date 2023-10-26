package com.ruturaj.quizappbackend.service;

import com.ruturaj.quizappbackend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    ResponseEntity<User> createUser(User user) throws Exception;

    ResponseEntity<User> getUser(String username);

    void deleteUser(Long userId);

    ResponseEntity<User> updateUser(User user, Long userId) throws Exception;

    ResponseEntity<User> getUserById(Long userId);

    ResponseEntity<List<User>> getAllUser();

    ResponseEntity<String> updatePassword(String email, String otp, String generatedOtp, String newPassword);
}
