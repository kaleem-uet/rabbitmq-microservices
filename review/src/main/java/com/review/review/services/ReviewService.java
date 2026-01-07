package com.review.review.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.review.review.entities.CompanyInfo;
import com.review.review.entities.Review;
import com.review.review.repository.CompanyInfoRepository;
import com.review.review.repository.ReviewRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CompanyInfoRepository companyInfoRepository;

    public ReviewService(ReviewRepository reviewRepository,
            CompanyInfoRepository companyInfoRepository) {
        this.reviewRepository = reviewRepository;
        this.companyInfoRepository = companyInfoRepository;
    }

    /**
     * Create a new review
     */
    @Transactional
    public Review createReview(Review review) {
        // Verify company exists and is active
        CompanyInfo companyInfo = companyInfoRepository.findById(review.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + review.getCompanyId()));

        if ("DELETED".equals(companyInfo.getStatus())) {
            throw new RuntimeException("Cannot create review for deleted company");
        }

        // Set company name from our local copy
        review.setCompanyName(companyInfo.getCompanyName());
        review.setReviewDate(LocalDateTime.now());
        review.setIsVerified(false); // Reviews start as unverified

        return reviewRepository.save(review);
    }

    /**
     * Get all reviews
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Get review by ID
     */
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    /**
     * Get all reviews for a specific company
     */
    public List<Review> getReviewsByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    /**
     * Get verified reviews for a company
     */
    public List<Review> getVerifiedReviewsByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyIdAndIsVerified(companyId, true);
    }

    /**
     * Verify a review (admin function)
     */
    @Transactional
    public Review verifyReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        review.setIsVerified(true);
        return reviewRepository.save(review);
    }

    /**
     * Update a review
     */
    @Transactional
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = getReviewById(id);

        review.setReviewerName(reviewDetails.getReviewerName());
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());

        return reviewRepository.save(review);
    }

    /**
     * Delete a review
     */
    @Transactional
    public void deleteReview(Long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }

    /**
     * Get all companies (synced from Company Service)
     */
    public List<CompanyInfo> getAllCompanies() {
        return companyInfoRepository.findAll();
    }

    /**
     * Get active companies only
     */
    public List<CompanyInfo> getActiveCompanies() {
        return companyInfoRepository.findByStatus("ACTIVE");
    }
}
