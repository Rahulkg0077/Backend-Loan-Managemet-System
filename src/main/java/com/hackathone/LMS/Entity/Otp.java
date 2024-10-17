package com.hackathone.LMS.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Otp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	private String otp;
	private LocalDateTime expiryTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	
	
}
