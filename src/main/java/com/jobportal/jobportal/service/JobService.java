package com.jobportal.jobportal.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.Skill;
@Service
public class JobService {
	 public int calculateMatch(Job job, Set<Skill> userSkills) {

	        return (int) job.getSkills()
	                .stream()
	                .filter(userSkills::contains)
	                .count();
	    }
}
