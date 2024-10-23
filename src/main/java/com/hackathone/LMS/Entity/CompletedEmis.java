package com.hackathone.LMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CompletedEmis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime emiDate;
    private double emiAmount;
    private LocalDateTime createdAt;
    private User createdBy;
    private LocalDateTime modifiedAt;
    private User modifiedBy;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    // Getters and Setters
}
