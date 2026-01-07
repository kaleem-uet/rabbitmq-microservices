package com.company.company.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.company.entities.Company;
import com.company.company.services.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyContoller {

    private final CompanyService companyService;

    public CompanyContoller(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Create a new company
     * POST /api/companies
     */

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    /**
     * Get all companies
     * GET /api/companies
     */
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    /**
     * Get company by ID
     * GET /api/companies/{id}
     */

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    /**
     * Update a company
     * PUT /api/companies/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id,
            @RequestBody Company companyDetails) {
        Company updatedCompany = companyService.updateCompany(id, companyDetails);
        return ResponseEntity.ok(updatedCompany);
    }

    /**
     * Delete a company
     * DELETE /api/companies/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get companies by industry
     * GET /api/companies/industry/{industry}
     */
    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<Company>> getCompaniesByIndustry(@PathVariable String industry) {
        List<Company> companies = companyService.getCompaniesByIndustry(industry);
        return ResponseEntity.ok(companies);
    }

}
