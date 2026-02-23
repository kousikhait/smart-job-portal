package com.jobportal.jobportal.controller;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/recruiter")
public class RecruiterController {
	
	private final JobRepository jobRepository;
	private final UserRepository userRepository;
	private final ApplicationRepository applicationRepository;
	private final JobService jobService;
	
	
	
	public RecruiterController(JobRepository jobRepository, UserRepository userRepository,
			ApplicationRepository applicationRepository,JobService jobService) {
		super();
		this.jobRepository = jobRepository;
		this.userRepository = userRepository;
		this.applicationRepository = applicationRepository;
		this.jobService=jobService;
	}

	
	@GetMapping("/dashboard")
	public String recruiterDashboard(HttpSession session, Model model) {

	    User recruiter = (User) session.getAttribute("loggedUser");

	    String roleName = recruiter != null && recruiter.getRole() != null
	            ? recruiter.getRole().getName()
	            : null;

	    if (recruiter == null || !"RECRUITER".equals(roleName)) {
	        return "redirect:/login";
	    }

	    model.addAttribute("applications",
	            applicationRepository.findByJobRecruiter(recruiter));

	    return "recruiter-dashboard";
	}


    @GetMapping("/add-job")
	public String addJobPage(Model model) {
	    model.addAttribute("job", new Job());
	    return "add-job";
	}

	@PostMapping("/add-job")
	public String saveJob(@ModelAttribute Job job,
			 HttpSession session) {

	    User recruiter = (User) session.getAttribute("loggedUser");

	    String roleName = recruiter != null && recruiter.getRole() != null
	            ? recruiter.getRole().getName()
	            : null;

	    if (recruiter == null || !"RECRUITER".equals(roleName)) {
	        return "redirect:/login";
	    }

        job.setRecruiter(recruiter);
        jobRepository.save(job);

	    return "redirect:/recruiter/jobs";
	}
	
    // Save Job
//    @PostMapping("/add-job")
//    public String saveJob(@ModelAttribute Job job,
//                          Authentication authentication) {
//
//        String email = authentication.getName();
//        User recruiter = userRepository.findByEmail(email).orElse(null);
//
//        job.setRecruiter(recruiter);
//
//        jobRepository.save(job);
//
//        return "redirect:/jobs-page";
//    }
	
	@PostMapping("/update-status/{id}")
	public String updateStatus(@PathVariable Long id,
	                           @RequestParam String status) {

	    Application application =
	            applicationRepository.findById(id).orElse(null);

	    if (application != null) {
	        application.setStatus(status);
	        applicationRepository.save(application);
	    }

	    return "redirect:/recruiter/dashboard";
	}
	
	@GetMapping("/resume/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> downloadResume(@PathVariable String fileName) throws IOException {

	    String uploadDir = System.getProperty("user.dir") + "/uploads/";
	    Path path = Paths.get(uploadDir).resolve(fileName).normalize();

	    Resource resource = new UrlResource(path.toUri());

	    if (!resource.exists()) {
	        throw new RuntimeException("File not found");
	    }

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=\"" + resource.getFilename() + "\"")
	            .body(resource);
	}
	
	@PostMapping("/delete-job/{id}")
	public String deleteJob(@PathVariable Long id) {

	    jobRepository.deleteById(id);
	    return "redirect:/recruiter/jobs";
	}

	@GetMapping("/jobs")
	public String recruiterJobs(HttpSession session, Model model) {

	    User recruiter = (User) session.getAttribute("loggedUser");

	    String roleName = recruiter != null && recruiter.getRole() != null
	            ? recruiter.getRole().getName()
	            : null;

	    if (recruiter == null || !"RECRUITER".equals(roleName)) {
	        return "redirect:/login";
	    }

	    model.addAttribute("jobs", jobRepository.findByRecruiter(recruiter));
	    return "recruiter-jobs";
	}
}
