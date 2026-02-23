package com.jobportal.jobportal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;
import com.jobportal.jobportal.service.JobService;

@Controller
public class RecruiterJobPageController {
	private final JobRepository jobRepository;
	private final UserRepository userRepository;
	private final ApplicationRepository applicationRepository;
	private final JobService jobService;
	
	public RecruiterJobPageController(JobRepository jobRepository,UserRepository userRepository,ApplicationRepository applicationRepository
			,JobService jobService) {
		this.jobRepository=jobRepository;
		this.userRepository=userRepository;
		this.applicationRepository=applicationRepository;
		this.jobService=jobService;
	}
	
	@GetMapping("/recruiter/jobs")
	public String recruiterJobs(Model model, Authentication authentication) {

	    String email = authentication.getName();
	    User recruiter = userRepository.findByEmail(email).orElse(null);

	    if (recruiter != null) {
	        model.addAttribute("jobs",
	                jobRepository.findByRecruiter(recruiter));
	    }

	    return "recruiter-jobs";
	}
}
