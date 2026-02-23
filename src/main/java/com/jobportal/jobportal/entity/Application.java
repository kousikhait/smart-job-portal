package com.jobportal.jobportal.entity;

//import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="applications")
public class Application {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="job_id")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
//	private LocalDateTime appliedAt;
	private String status;
	
	private String resumeFileName;
	
//	constructor
	public Application() {
		
	}

	public Application( Job job, User user, String status) {
		super();
		this.job = job;
		this.user = user;
		this.status = status;
//		this.appliedAt = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public LocalDateTime getAppliedAt() {
//		return appliedAt;
//	}
//
//	public void setAppliedAt(LocalDateTime appliedAt) {
//		this.appliedAt = appliedAt;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResumeFileName() {
		return resumeFileName;
	}

	public void setResumeFileName(String resumeFileName) {
		this.resumeFileName = resumeFileName;
	}

//	public void setId(Long id) {
//		this.id = id;
//	}
	
	
	
	
	
}

