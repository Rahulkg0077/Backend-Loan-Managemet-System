package com.hackathone.LMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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
	private User createdBy;
	private LocalDateTime modifiedAt;
	private User modifiedBy;
	private Double interest;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "loan_type_id")
	private LoanType loanType;

	@ManyToOne
	@JoinColumn(name = "loan_status_id")
	private LoanStatus loanStatus;



//	@OneToMany(mappedBy = "loan")
//	private List<CompletedEmis> completedEmis;

	// Getters and Setters
}
