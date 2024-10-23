package com.hackathone.LMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class LoanType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String displayName;
    private Double interestRate;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

//    @OneToMany(mappedBy = "loanType")
//    private List<Loan> loans;

    // Getters and Setters
}
