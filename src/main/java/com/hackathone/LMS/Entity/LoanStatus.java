package com.hackathone.LMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class LoanStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private User createdBy;
    private LocalDateTime modifiedAt;
    private User modifiedBy;

//    @OneToMany(mappedBy = "loanStatus")
//    private List<Loan> loans;

    // Getters and Setters
}
