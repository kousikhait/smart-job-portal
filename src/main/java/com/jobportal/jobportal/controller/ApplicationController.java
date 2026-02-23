package com.jobportal.jobportal.controller;

import java.io.File;
import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Optional;

//import org.apache.el.stream.Optional;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.jobportal.entity.*;
import com.jobportal.jobportal.repository.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationController(ApplicationRepository applicationRepository,
                                 JobRepository jobRepository,
                                 UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String email,
//                        @RequestParam String password,
//                        HttpSession session) {
//
//    	Optional<User> optionalUser = userRepository.findByEmail(email);
//
//    	if(optionalUser.isPresent()) {
//    	    User user = optionalUser.get();
//
//    	    if(user.getPassword().equals(password)) {
//    	        session.setAttribute("user", user);
//    	        return "redirect:/dashboard";
//    	    }
//    	}
//
//        return "login";
//    }
}