package com.jobportal.jobportal.controller;

import java.io.File;
import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Optional;

//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.jobportal.entity.*;
import com.jobportal.jobportal.repository.*;

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

//    @PostMapping("/apply/{jobId}")
//    public String applyJob(@PathVariable Long jobId, Authentication authentication) {
//
//        String email = authentication.getName();
//
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        Optional<Job> optionalJob = jobRepository.findById(jobId);
//
//        if (optionalUser.isPresent() && optionalJob.isPresent()) {
//
//            User user = optionalUser.get();
//            Job job = optionalJob.get();
//
//            if (!applicationRepository.existsByUserAndJob(user, job)) {
//
//                Application application = new Application();
//                application.setUser(user);
//                application.setJob(job);
//                application.setStatus("APPLIED");
//
//                applicationRepository.save(application);
//            }
//        }
//
//        return "redirect:/jobs-page";
//    }
    
    @PostMapping("/apply/{jobId}")
    public String applyJob(@PathVariable Long jobId,
                           @RequestParam("resume") MultipartFile file,
                           Authentication authentication) throws IOException {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        Job job = jobRepository.findById(jobId).orElse(null);

        if (user != null && job != null) {

            if (!applicationRepository.existsByUserAndJob(user, job)) {

            	String uploadDir = System.getProperty("user.dir") + "/uploads/";
            	File uploadPath = new File(uploadDir);

            	if (!uploadPath.exists()) {
            	    uploadPath.mkdirs();
            	}

            	String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            	file.transferTo(new File(uploadDir + fileName));

                Application application = new Application();
                application.setUser(user);
                application.setJob(job);
                application.setStatus("APPLIED");
                application.setResumeFileName(fileName);

                applicationRepository.save(application);
            }
        }

        return "redirect:/jobs-page";
    }
    
//    @GetMapping("/resume/{fileName:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> downloadResume(@PathVariable String fileName) throws IOException {
//
//        String uploadDir = System.getProperty("user.dir") + "/uploads/";
//        Path path = Paths.get(uploadDir).resolve(fileName).normalize();
//
//        Resource resource = new UrlResource(path.toUri());
//
//        if (!resource.exists()) {
//            throw new RuntimeException("File not found");
//        }
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
}