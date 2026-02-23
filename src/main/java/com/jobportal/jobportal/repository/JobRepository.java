package com.jobportal.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.User;

public interface JobRepository extends JpaRepository<Job, Long>{
	List<Job>findByRecruiter(User user);
}
