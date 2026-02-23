package com.jobportal.jobportal.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;

import com.jobportal.jobportal.entity.Application;
import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.Skill;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;
import com.jobportal.jobportal.service.JobService;

import jakarta.servlet.http.HttpSession;

@Controller
//@RequestMapping("/api/jobs")
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
	
//	@GetMapping
//	public List<Job>getAllJobs(){
//		return jobRepository.findAll();
//	}
	
	@GetMapping("/recommended")
	public List<Job> getRecommendedJobs(HttpSession session) {
		
		User sessionUser=(User)session.getAttribute("loggedUser");


	    if (sessionUser == null) {
	        return List.of();
	    }

	    // Reload user with initialized skills
	    User user = userRepository.findWithSkillsById(sessionUser.getId()).orElse(sessionUser);
	    session.setAttribute("loggedUser", user);

	    Set<Skill> userSkills = user.getSkills();

	    return jobRepository.findAll()
	            .stream()
	            .sorted((j1, j2) ->
	                    jobService.calculateMatch(j2, userSkills)
	                    - jobService.calculateMatch(j1, userSkills))
	            .toList();
	}
	

	
	@PostMapping("/apply/{jobId}")
	public String applyJob(@PathVariable long jobId,
	                       @RequestParam("resume") MultipartFile file,
	                       HttpSession session) throws IOException {

	    User user = (User) session.getAttribute("loggedUser");

	    String roleName = user != null && user.getRole() != null
	            ? user.getRole().getName()
	            : null;

	    if (user == null || !"JOB_SEEKER".equals(roleName)) {
	        return "redirect:/login";
	    }

	    Job job = jobRepository.findById(jobId).orElse(null);

	    if (job == null) {
	        return "redirect:/jobs-page";
	    }

	    Long userId = user.getId();
	    if (userId == null) {
	        session.invalidate();
	        return "redirect:/login";
	    }

	    if (applicationRepository.existsByUserIdAndJobId(userId, jobId)) {
	        return "redirect:/jobs-page?alreadyApplied";
	    }

	    String uploadDir = System.getProperty("user.dir") + "/uploads/";
	    File dir = new File(uploadDir);
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	    File destination = new File(uploadDir + fileName);
	    file.transferTo(destination);

	    Application application = new Application(job, user, "APPLIED");
	    application.setResumeFileName(fileName);
	    applicationRepository.save(application);

	    return "redirect:/jobs-page?success";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/";
	}
	

}
