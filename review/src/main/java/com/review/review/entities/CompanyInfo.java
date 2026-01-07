package com.review.review.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfo {

    @Id
    private Long companyId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String industry;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String status; // ACTIVE, DELETED
}