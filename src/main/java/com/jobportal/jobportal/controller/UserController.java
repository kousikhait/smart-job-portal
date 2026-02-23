package com.jobportal.jobportal.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}