package com.hackathone.LMS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathone.LMS.Entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	public Users findByPanNo(String panNo);
}
