package com.ruturaj.quizappbackend.controller;

import com.ruturaj.quizappbackend.entity.User;
import com.ruturaj.quizappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "https://ruturaj220.github.io/quiz-app-frontend/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        return this.userService.createUser(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        return this.userService.getUser(username);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        return this.userService.getUserById(userId);
    }
       

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        this.userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable("userId") Long userId) throws Exception {
        return this.userService.updateUser(user,userId);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser(){
        return this.userService.getAllUser();
    }


}
