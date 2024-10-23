package com.hackathone.LMS.Entity;

import com.hackathone.LMS.Entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class KYC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String aadharNo;
    private String address;
    private Integer cibilScore;
    private LocalDateTime createdAt;
    private User createdBy;
    private LocalDateTime modifiedAt;
    private User modifiedBy;

//    @OneToOne(mappedBy = "kyc")
//    private User user;

    // Getters and Setters
}
