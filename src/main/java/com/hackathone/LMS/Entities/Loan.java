package com.hackathone.LMS.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Loan {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double loanAmount;
	private Integer tenureInYears;
	private Integer tenureInMonths;
	private LocalDateTime emiDebitedDate;
	private LocalDateTime createdAt;	
	private LocalDateTime modifiedAt;
	private Double interest;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne
	@JoinColumn(name = "loan_type_id")
	private LoanType loanType;

	@ManyToOne
	@JoinColumn(name = "loan_status_id")
	private LoanStatus loanStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getTenureInYears() {
		return tenureInYears;
	}

	public void setTenureInYears(Integer tenureInYears) {
		this.tenureInYears = tenureInYears;
	}

	public Integer getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(Integer tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public LocalDateTime getEmiDebitedDate() {
		return emiDebitedDate;
	}

	public void setEmiDebitedDate(LocalDateTime emiDebitedDate) {
		this.emiDebitedDate = emiDebitedDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}
	
}
