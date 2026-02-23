package com.jobportal.jobportal.entity;

import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="jobs")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private String location;
	private double salary;
	@ManyToMany
	@JoinTable(
			name="job_skills",
			joinColumns = @JoinColumn(name="job_id"),
			inverseJoinColumns = @JoinColumn(name="skill_id"))
	private Set<Skill>skills;
	@ManyToOne
	@JoinColumn(name="recruiter_id")
	private User recruiter;
	public Job() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Set<Skill> getSkills() {
		return skills;
	}
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	public User getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(User recruiter) {
		this.recruiter = recruiter;
	}
	public Job( String title, String description, String location, double salary, Set<Skill> skills,
			User recruiter) {
		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.salary = salary;
		this.skills = skills;
		this.recruiter = recruiter;
	}
	
	

}
