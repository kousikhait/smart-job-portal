package com.jobportal.jobportal.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jobportal.jobportal.entity.Application;
import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;

@Controller
public class PageController {
	 private final JobRepository jobRepository;
	 private final UserRepository userRepository;
	 private final ApplicationRepository applicationRepository;

	 

	    public PageController(JobRepository jobRepository, UserRepository userRepository,
			ApplicationRepository applicationRepository) {
		super();
		this.jobRepository = jobRepository;
		this.userRepository = userRepository;
		this.applicationRepository = applicationRepository;
	}

		@GetMapping("/")
	    public String home() {
	        return "home";
	    }
		
		@GetMapping("/login")
		public String login() {
			return "login";
		}

		@GetMapping("/jobs-page")
		public String jobs(Model model, Authentication authentication) {

		    String email = authentication.getName();
		    User user = userRepository.findByEmail(email).orElse(null);

		    List<Job> jobs = jobRepository.findAll();
		    model.addAttribute("jobs", jobs);

		    if (user != null) {

		        List<Application> applications =
		                applicationRepository.findByUser(user);

		        Set<Long> appliedJobIds = applications.stream()
		                .map(app -> app.getJob().getId())
		                .collect(Collectors.toSet());

		        model.addAttribute("appliedJobIds", appliedJobIds);
		    }

		    return "jobs";
		}
		
		@GetMapping("/recruiter/dashboard")
		public String recruiterDashboard(Model model, Authentication authentication) {

		    String email = authentication.getName();
		    User recruiter = userRepository.findByEmail(email).orElse(null);

		    if (recruiter != null) {
		        model.addAttribute("applications",
		                applicationRepository.findByJobRecruiter(recruiter));
		    }

		    return "recruiter-dashboard";
		}
}
