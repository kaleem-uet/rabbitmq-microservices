package com.company.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.company.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByIndustry(String industry);

    List<Company> findByLocation(String location);

}
