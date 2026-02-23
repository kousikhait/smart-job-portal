package com.jobportal.jobportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.jobportal.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long>{

	Optional<Skill> findByName(String name);
}
