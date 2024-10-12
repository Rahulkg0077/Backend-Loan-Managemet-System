package com.hackathone.LMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hackathone.LMS.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByPanId(String panId);
}
