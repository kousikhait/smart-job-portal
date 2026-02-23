package com.jobportal.jobportal.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobportal.jobportal.entity.Role;
import com.jobportal.jobportal.entity.Skill;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.RoleRepository;
import com.jobportal.jobportal.repository.SkillRepository;
import com.jobportal.jobportal.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {
	 private final JobRepository jobRepository;
	 private final UserRepository userRepository;
	 private final RoleRepository roleRepository;
	 private final SkillRepository skillRepository;

	    public PageController(JobRepository jobRepository,
	                          RoleRepository roleRepository,
	                          UserRepository userRepository,
	                          SkillRepository skillRepository) {
		this.jobRepository = jobRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.skillRepository = skillRepository;
	}

	    @GetMapping("/")
	    public String home(HttpSession session) {

	        User user = (User) session.getAttribute("loggedUser");

	        if (user != null) {
	            String roleName = user.getRole() != null ? user.getRole().getName() : null;

	            if ("JOB_SEEKER".equals(roleName)) {
	                return "redirect:/jobs-page";
	            } else if ("RECRUITER".equals(roleName)) {
	                return "redirect:/recruiter/dashboard";
	            }
	        }

	        return "home";
	    }
		
	    @GetMapping("/signup")
	    public String signupPage(Model model) {
	        model.addAttribute("user", new User());
	        return "signup";
	    }

	    @PostMapping("/signup")
	    public String signup(@ModelAttribute User user,
	                         @RequestParam("roleName") String roleName,
	                         @RequestParam(name = "skillsInput", required = false) String skillsInput,
	                         Model model) {

	        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	            model.addAttribute("error", "Email already exists");
	            return "signup";
	        }

	        Role role = roleRepository.findByName(roleName);

	        if (role == null) {
	            model.addAttribute("error", "Invalid role selected");
	            return "signup";
	        }

	        user.setRole(role);

	        if ("JOB_SEEKER".equals(roleName) && skillsInput != null && !skillsInput.isBlank()) {

	            Set<String> uniqueNames = new HashSet<>();
	            Set<Skill> userSkills = new HashSet<>();

	            Arrays.stream(skillsInput.split(","))
	                    .map(String::trim)
	                    .filter(s -> !s.isEmpty())
	                    .forEach(raw -> {
	                        String normalized = raw.toLowerCase();
	                        if (uniqueNames.add(normalized)) {
	                            Skill skill = skillRepository.findByName(raw)
	                                    .orElseGet(() -> skillRepository.save(new Skill(raw)));
	                            userSkills.add(skill);
	                        }
	                    });

	            user.setSkills(userSkills);
	        }

	        userRepository.save(user);

	        return "redirect:/login";
	    }
		
		@GetMapping("/login")
		public String loginPage() {
		    return "login";
		}

		@PostMapping("/login")
		public String login(@RequestParam String email,
		                    @RequestParam String password,
		                    HttpSession session,
		                    Model model) {

		    User user = userRepository.findByEmail(email).orElse(null);

		    if (user == null || !user.getPassword().equals(password)) {
		        model.addAttribute("error", "Invalid email or password");
		        return "login";
		    }

		    session.setAttribute("loggedUser", user);

		    String roleName = user.getRole() != null ? user.getRole().getName() : null;

		    if ("JOB_SEEKER".equals(roleName)) {
		        return "redirect:/jobs-page";
		    } else if ("RECRUITER".equals(roleName)) {
		        return "redirect:/recruiter/dashboard";
		    } else {
		        return "redirect:/";
		    }
		}
		
		
		
		
}
