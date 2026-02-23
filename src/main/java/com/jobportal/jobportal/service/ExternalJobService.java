package com.jobportal.jobportal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.Skill;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.SkillRepository;

@Service
public class ExternalJobService {

    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;

    public ExternalJobService(JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    public int syncExternalJobs() {
        List<ExternalJob> externalJobs = new ArrayList<>();

        externalJobs.addAll(fetchFromLinkedIn());
        externalJobs.addAll(fetchFromNaukri());
        externalJobs.addAll(fetchFromIndeed());

        int created = 0;

        for (ExternalJob ext : externalJobs) {
            Job job = new Job();
            job.setTitle(ext.title());
            job.setDescription(ext.description());
            job.setLocation(ext.location());
            job.setSalary(ext.salary());
            job.setApplyUrl(ext.applyUrl());

            Set<Skill> jobSkills = new HashSet<>();
            for (String name : ext.skills()) {
                if (name == null || name.isBlank()) {
                    continue;
                }
                String trimmed = name.trim();
                Skill skill = skillRepository.findByName(trimmed)
                        .orElseGet(() -> skillRepository.save(new Skill(trimmed)));
                jobSkills.add(skill);
            }
            job.setSkills(jobSkills);

            jobRepository.save(job);
            created++;
        }

        return created;
    }

    private List<ExternalJob> fetchFromLinkedIn() {
        return List.of(
                new ExternalJob(
                        "LINKEDIN",
                        "Senior Java Developer",
                        "Build scalable backend services with Spring Boot. Experience with microservices and cloud preferred.",
                        "Bangalore, India",
                        1200000,
                        List.of("Java", "Spring Boot", "MySQL", "React"),
                        "https://www.linkedin.com/jobs/search/?keywords=java%20developer"
                ),
                new ExternalJob(
                        "LINKEDIN",
                        "Full Stack Engineer",
                        "Work on web applications using Java, Spring, and modern front-end frameworks.",
                        "Mumbai, India",
                        1000000,
                        List.of("Java", "Spring Boot", "React"),
                        "https://www.linkedin.com/jobs/search/?keywords=full%20stack"
                )
        );
    }

    private List<ExternalJob> fetchFromNaukri() {
        return List.of(
                new ExternalJob(
                        "NAUKRI",
                        "Backend Developer - Spring Boot",
                        "Looking for developers with strong experience in Spring Boot, REST APIs and databases.",
                        "Kolkata, India",
                        800000,
                        List.of("Java", "Spring Boot", "MySQL"),
                        "https://www.naukri.com/jobs-in-kolkata"
                ),
                new ExternalJob(
                        "NAUKRI",
                        "Software Engineer - Java",
                        "Join our product team. Java, JPA, and SQL experience required.",
                        "Hyderabad, India",
                        900000,
                        List.of("Java", "Spring Boot", "MySQL"),
                        "https://www.naukri.com/jobs-in-hyderabad"
                )
        );
    }

    private List<ExternalJob> fetchFromIndeed() {
        return List.of(
                new ExternalJob(
                        "INDEED",
                        "Java Developer",
                        "Develop and maintain enterprise Java applications. Spring and database skills needed.",
                        "Chennai, India",
                        750000,
                        List.of("Java", "Spring Boot", "React"),
                        "https://in.indeed.com/jobs?q=java+developer"
                ),
                new ExternalJob(
                        "INDEED",
                        "Backend Engineer",
                        "Design and implement REST APIs and services. Experience with Spring Boot and PostgreSQL.",
                        "Pune, India",
                        950000,
                        List.of("Java", "Spring Boot", "MySQL"),
                        "https://in.indeed.com/jobs?q=backend+engineer"
                )
        );
    }

    public record ExternalJob(
            String source,
            String title,
            String description,
            String location,
            double salary,
            List<String> skills,
            String applyUrl
    ) {
    }
}

