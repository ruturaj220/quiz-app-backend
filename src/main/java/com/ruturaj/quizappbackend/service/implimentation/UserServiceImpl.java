package com.ruturaj.quizappbackend.service.implimentation;

import com.ruturaj.quizappbackend.entity.User;

import com.ruturaj.quizappbackend.exception.UserNotFoundException;
import com.ruturaj.quizappbackend.repository.UserRepository;
import com.ruturaj.quizappbackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
;import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public ResponseEntity<User> createUser(User user) throws Exception {
        try {
            User localByUsername = this.userRepository.findByUsername(user.getUsername());
            User localByEmail = this.userRepository.findByEmail(user.getEmail());

            if (localByUsername != null) {
                logger.error("User with this username already exists !");
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else if (localByEmail != null) {
                logger.error("User with this email already exists !");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                User savedUser = this.userRepository.save(user);
                return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            // Handle the exception here, you can log it or perform other actions
            logger.error("An error occurred while creating a user", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Return an appropriate response for the exception
        }
    }


    @Override
    public ResponseEntity<User> getUser(String username) {
        try {
            User user = this.userRepository.findByUsername(username);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle the exception here, you can log it or perform other actions
            logger.error("An error occurred while fetching a user by username: " + username, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Return an appropriate response for the exception
        }
    }
    @Override
    public void deleteUser(Long userId) {

        if(!this.userRepository.existsById(userId)){
            throw new UserNotFoundException(userId);
        }else{
            this.userRepository.deleteById(userId);
        }


    }

    @Override
    public ResponseEntity<User> updateUser(User newUser, Long userId) throws Exception {
        try {
            return this.userRepository.findById(userId).map(user -> {
                user.setFirstName(newUser.getFirstName());
                user.setLastName(newUser.getLastName());
                user.setPassword(newUser.getPassword());
                user.setEmail(newUser.getEmail());

                User updatedUser = this.userRepository.save(user);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // Handle the exception here, you can log it or perform other actions
            logger.error("An error occurred while updating a user with ID: " + userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Return an appropriate response for the exception
        }
    }

    @Override
    public ResponseEntity<User> getUserById(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        User user = userOptional.orElse(null); // Get the User or null if not present

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<User>> getAllUser() {
        try {
            List<User> users = this.userRepository.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception and return an appropriate HTTP status code
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean enteredOtpMatches(String enteredOtp, String generatedOtp) {
        return enteredOtp.equals(generatedOtp);
    }
    @Override
    public ResponseEntity<String> updatePassword(String email, String enteredOtp, String generatedOtp, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with this email.");
        }

        if (enteredOtpMatches(enteredOtp, generatedOtp)) { // Compare entered OTP with the generated OTP
            try {
                user.setPassword(newPassword);
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
            } catch (Exception e) {
                logger.error("An error occurred while updating the password for the user with email: " + email, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the password.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }


}
