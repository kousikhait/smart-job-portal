package com.jobportal.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.jobportal.entity.Application;
import com.jobportal.jobportal.entity.Job;
import com.jobportal.jobportal.entity.User;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	boolean existsByUserAndJob(User user, Job job);
	List<Application> findByJobRecruiterId(Long recruiterId);
	boolean existsByUserIdAndJobId(Long userId, Long jobId);
	List<Application> findByUser(User user);
	List<Application> findByJobRecruiter(User recruiter);

}
