package com.jobportal.jobportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import com.jobportal.jobportal.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@EntityGraph(attributePaths = "skills")
	Optional<User> findWithSkillsById(Long id);

}
