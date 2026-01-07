package com.review.review.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.review.review.entities.CompanyInfo;
import com.review.review.entities.Review;
import com.review.review.services.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Create a new review
     * POST /api/reviews
     */
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review createdReview = reviewService.createReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    /**
     * Get all reviews
     * GET /api/reviews
     */
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get review by ID
     * GET /api/reviews/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    /**
     * Get reviews by company ID
     * GET /api/reviews/company/{companyId}
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Review>> getReviewsByCompanyId(@PathVariable Long companyId) {
        List<Review> reviews = reviewService.getReviewsByCompanyId(companyId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get verified reviews by company ID
     * GET /api/reviews/company/{companyId}/verified
     */
    @GetMapping("/company/{companyId}/verified")
    public ResponseEntity<List<Review>> getVerifiedReviewsByCompanyId(@PathVariable Long companyId) {
        List<Review> reviews = reviewService.getVerifiedReviewsByCompanyId(companyId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Verify a review
     * PATCH /api/reviews/{id}/verify
     */
    @PatchMapping("/{id}/verify")
    public ResponseEntity<Review> verifyReview(@PathVariable Long id) {
        Review verifiedReview = reviewService.verifyReview(id);
        return ResponseEntity.ok(verifiedReview);
    }

    /**
     * Update a review
     * PUT /api/reviews/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id,
            @RequestBody Review reviewDetails) {
        Review updatedReview = reviewService.updateReview(id, reviewDetails);
        return ResponseEntity.ok(updatedReview);
    }

    /**
     * Delete a review
     * DELETE /api/reviews/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all companies (synced from Company Service)
     * GET /api/reviews/companies
     */
    @GetMapping("/companies")
    public ResponseEntity<List<CompanyInfo>> getAllCompanies() {
        List<CompanyInfo> companies = reviewService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    /**
     * Get active companies only
     * GET /api/reviews/companies/active
     */
    @GetMapping("/companies/active")
    public ResponseEntity<List<CompanyInfo>> getActiveCompanies() {
        List<CompanyInfo> companies = reviewService.getActiveCompanies();
        return ResponseEntity.ok(companies);
    }
}
