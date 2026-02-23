package com.jobportal.jobportal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.Role;
import com.jobportal.jobportal.entity.Skill;
import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.RoleRepository;
import com.jobportal.jobportal.repository.SkillRepository;
import com.jobportal.jobportal.repository.UserRepository;

@SpringBootApplication
public class JobportalApplication {

	 public static void main(String[] args) {
	        SpringApplication.run(JobportalApplication.class, args);
	 }
	    

	 @Bean
	 CommandLineRunner init(RoleRepository roleRepository,
	                        UserRepository userRepository,
	                        SkillRepository skillRepository,
	                        JobRepository jobRepository,
	                        PasswordEncoder passwordEncoder) {

	     return args -> {

	         if (roleRepository.count() == 0) {
	             roleRepository.save(new Role("ADMIN"));
	             roleRepository.save(new Role("RECRUITER"));
	             roleRepository.save(new Role("JOB_SEEKER"));
	         }

	         if (skillRepository.count() == 0) {
	             skillRepository.save(new Skill("Java"));
	             skillRepository.save(new Skill("Spring Boot"));
	             skillRepository.save(new Skill("MySQL"));
	             skillRepository.save(new Skill("React"));
	         }
	         
	         Set<Skill> skills = new HashSet<>(skillRepository.findAll());
	         Role jobSeekerRole = roleRepository.findAll()
	                 .stream()
	                 .filter(r -> r.getName().equals("JOB_SEEKER"))
	                 .findFirst()
	                 .orElse(null);

	         if (jobSeekerRole != null && userRepository.count() == 0) {

	             // Fetch some skills
//	             Set<Skill> skills = new HashSet<>(skillRepository.findAll());

	             User user = new User(
	                     "Kousik",
	                     "kousik@gmail.com",
	                     passwordEncoder.encode("1234"),
	                     0,
	                     jobSeekerRole
	             );

	             user.setSkills(skills);   // 🔥 Assign skills here

	             userRepository.save(user);
	         }
	         
	         User recruiter=userRepository.findByEmail("kousik@gmail.com").orElse(null);
//	         fetch some skills
	         Set<Skill>jobSkills=new HashSet<>(skillRepository.findAll());
	         if(recruiter!=null) {
	        	 Job job=new Job(
	        		 "Backend Developer",
	        		 "Spring Boot + MySQL developer required",
	        		 "kolkata",
	        		 600000.00,
	        		 jobSkills,
	        		 recruiter
	        		 );
	        	 
	        	 jobRepository.save(job);
	        	 }
	         

	         
	     };
	 }
}
	 
