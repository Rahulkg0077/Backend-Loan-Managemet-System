package com.hackathone.LMS.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
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

    @Override
	public String toString() {
		return "LoanDump [id=" + id + ", loanId=" + loanId + ", userId=" + userId + ", loanAmount=" + loanAmount
				+ ", tenureInMonths=" + tenureInMonths + ", interestRate=" + interestRate + ", emi=" + emi
				+ ", totalPendingEmis=" + totalPendingEmis + ", loanStatus=" + loanStatus + ", rejectionReason="
				+ rejectionReason + ", createdAt=" + createdAt + "]";
	}

    
}
