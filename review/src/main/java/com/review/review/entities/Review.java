package com.review.review.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String reviewerName;

    @Column(nullable = false)
    private Integer rating; // 1 to 5

    @Column(length = 2000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime reviewDate;

    @Column(nullable = false)
    private Boolean isVerified = false;
}
