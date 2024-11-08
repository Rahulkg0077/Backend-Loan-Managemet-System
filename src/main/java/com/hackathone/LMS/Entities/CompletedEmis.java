package com.hackathone.LMS.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CompletedEmis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime emiDate;
    private double emiAmount;
    private LocalDateTime createdAt;
    
    @OneToOne 
    @JoinColumn(name = "createdBy", referencedColumnName = "id") 
    private Users createdBy;
    
    private LocalDateTime modifiedAt;
    
    @OneToOne 
    @JoinColumn(name = "modifiedBy", referencedColumnName = "id") 
    private Users modifiedBy;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    // Getters and Setters
}
