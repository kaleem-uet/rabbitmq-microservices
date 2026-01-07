package com.review.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.review.review.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCompanyId(Long companyId);

    List<Review> findByCompanyIdAndIsVerified(Long companyId, Boolean isVerified);

    List<Review> findByRatingGreaterThanEqual(Integer rating);

}