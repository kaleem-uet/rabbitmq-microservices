package com.review.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.review.review.entities.CompanyInfo;

@Repository
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {
    List<CompanyInfo> findByStatus(String status);
    List<CompanyInfo> findByIndustry(String industry);
}
