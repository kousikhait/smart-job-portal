package com.jobportal.jobportal.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.jobportal.jobportal.entity.Application;
import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.Skill;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;
import com.jobportal.jobportal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
	private final JobRepository jobRepository;
	private final UserRepository userRepository;
	private final ApplicationRepository applicationRepository;
	private final JobService jobService;
	
	public JobController(JobRepository jobRepository,UserRepository userRepository,ApplicationRepository applicationRepository
			,JobService jobService) {
		this.jobRepository=jobRepository;
		this.userRepository=userRepository;
		this.applicationRepository=applicationRepository;
		this.jobService=jobService;
	}
	
	@GetMapping
	public List<Job>getAllJobs(){
		return jobRepository.findAll();
	}
	
	@GetMapping("/recommended")
	public List<Job> getRecommendedJobs() {
		

		Authentication authentication =
		        SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElse(null);

	    if (user == null) {
	        return List.of();
	    }

	    Set<Skill> userSkills = user.getSkills();

	    return jobRepository.findAll()
	            .stream()
	            .sorted((j1, j2) ->
	                    jobService.calculateMatch(j2, userSkills)
	                    - jobService.calculateMatch(j1, userSkills))
	            .toList();
	}
	
//	private int calculateMatch(Job job, Set<Skill> userSkills) {
//
//	    Set<Skill> jobSkills = job.getSkills();
//
//	    long matched = jobSkills.stream()
//	            .filter(userSkills::contains)
//	            .count();
//
//	    return (int) matched;
//	}
	
	@PostMapping("/{jobId}/apply")
	public String applyToJob(@PathVariable Long jobId) {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElse(null);

	    Job job = jobRepository.findById(jobId).orElse(null);

	    if (user == null || job == null) {
	        return "User or Job not found";
	    }
	    
	    if (applicationRepository
	            .existsByUserIdAndJobId(user.getId(), job.getId())) {
	        return "Already applied!";
	    }

	    Application application =
	            new Application(job, user, "APPLIED");

	    applicationRepository.save(application);

	    return "Applied Successfully!";
	    
	  
	}
	
	@GetMapping("/recruiter/add-job")
	public String addJobPage(Model model) {
	    model.addAttribute("job", new Job());
	    return "add-job";
	}

	@PostMapping("/recruiter/add-job")
	public String saveJob(@ModelAttribute Job job,
	                      Authentication authentication) {

	    String email = authentication.getName();
	    User recruiter = userRepository.findByEmail(email).orElse(null);

	    if (recruiter != null) {
	        job.setRecruiter(recruiter);
	        jobRepository.save(job);
	    }

	    return "redirect:/recruiter/jobs";
	}
	
	@PostMapping("/recruiter/delete-job/{id}")
	public String deleteJob(@PathVariable Long id) {

	    jobRepository.deleteById(id);
	    return "redirect:/recruiter/jobs";
	}
	
	
	
	

}
