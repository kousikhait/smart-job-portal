package com.jobportal.jobportal.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jobportal.jobportal.entity.Application;
import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;
import com.jobportal.jobportal.service.JobService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class UserController {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final JobService jobService;

    public UserController(UserRepository userRepository,
                          JobRepository jobRepository,
                          ApplicationRepository applicationRepository,
                          JobService jobService) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.jobService = jobService;
    }

    // Jobs page for job seekers
    @GetMapping("/jobs-page")
    public String jobs(HttpSession session, Model model) {

        User sessionUser = (User) session.getAttribute("loggedUser");

        String roleName = sessionUser != null && sessionUser.getRole() != null
                ? sessionUser.getRole().getName()
                : null;

        if (sessionUser == null || !"JOB_SEEKER".equals(roleName)) {
            return "redirect:/login";
        }

        // Reload user with skills initialized (session user is detached)
        User user = userRepository.findWithSkillsById(sessionUser.getId()).orElse(sessionUser);
        session.setAttribute("loggedUser", user);

        List<Application> applications = applicationRepository.findByUser(user);
        Set<Long> appliedJobIds = applications.stream()
                .map(app -> app.getJob().getId())
                .collect(Collectors.toSet());

        List<Job> allJobs = jobRepository.findAll();

        List<Job> recommendedJobs = allJobs.stream()
                .sorted(Comparator.comparingInt(
                        (Job j) -> jobService.calculateMatch(j, user.getSkills() != null ? user.getSkills() : Set.of())
                ).reversed())
                .collect(Collectors.toList());

        model.addAttribute("jobs", allJobs);
        model.addAttribute("recommendedJobs", recommendedJobs);
        model.addAttribute("appliedJobIds", appliedJobIds);
        return "jobs";
    }
}