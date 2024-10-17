package com.hackathone.LMS.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LoanDumpForCompleted")
public class LoanDumpForCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanId;
    private Long userId;
    private Double loanAmount;
    private Integer tenureInMonths;
    private Double interestRate;
    private Double emi;
    private Integer totalPendingEmis;
    private String loanStatus;
    private String rejectionReason;
    private LocalDateTime createdAt;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getTenureInMonths() {
		return tenureInMonths;
	}
	public void setTenureInMonths(Integer tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public Double getEmi() {
		return emi;
	}
	public void setEmi(Double emi) {
		this.emi = emi;
	}
	public Integer getTotalPendingEmis() {
		return totalPendingEmis;
	}
	public void setTotalPendingEmis(Integer totalPendingEmis) {
		this.totalPendingEmis = totalPendingEmis;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "LoanDump [id=" + id + ", loanId=" + loanId + ", userId=" + userId + ", loanAmount=" + loanAmount
				+ ", tenureInMonths=" + tenureInMonths + ", interestRate=" + interestRate + ", emi=" + emi
				+ ", totalPendingEmis=" + totalPendingEmis + ", loanStatus=" + loanStatus + ", rejectionReason="
				+ rejectionReason + ", createdAt=" + createdAt + "]";
	}

    
}
