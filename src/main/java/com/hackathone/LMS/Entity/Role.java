package com.hackathone.LMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String displayName;
    private LocalDateTime createdAt;
    private User createdBy;
    private LocalDateTime modifiedAt;
    private User modifiedBy;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    // Getters and Setters
}
