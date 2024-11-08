package com.hackathone.LMS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathone.LMS.Entities.UserRole;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long>{

	public UserRole findByName(String name);
}
