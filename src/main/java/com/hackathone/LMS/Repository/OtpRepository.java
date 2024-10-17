package com.hackathone.LMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathone.LMS.Entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

	Optional<Otp> findByEmail(String mail);
}
