package com.jobportal.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobportal.jobportal.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(String name);
	
}
