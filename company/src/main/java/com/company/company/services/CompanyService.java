package com.company.company.services;

import org.springframework.stereotype.Service;
import com.company.company.dtos.CompanyEvent;
import com.company.company.entities.Company;
import com.company.company.messages.RabbitMQPublisher;
import com.company.company.repository.CompanyRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RabbitMQPublisher rabbitMQPublisher;

    public CompanyService(CompanyRepository companyRepository,
            RabbitMQPublisher rabbitMQPublisher) {
        this.companyRepository = companyRepository;
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

    /**
     * Create a new company and publish an event
     */
    @Transactional
    public Company createCompany(Company company) {
        Company savedCompany = companyRepository.save(company);

        // Publish event to RabbitMQ
        CompanyEvent event = new CompanyEvent(
                savedCompany.getId(),
                savedCompany.getName(),
                savedCompany.getIndustry(),
                savedCompany.getDescription(),
                savedCompany.getEmail(),
                savedCompany.getLocation(),
                "CREATED");

        rabbitMQPublisher.publishCompanyEvent(event);

        return savedCompany;
    }

    /**
     * Update an existing company and publish an event
     */
    @Transactional
    public Company updateCompany(Long id, Company company) {
        Company savedCompany = companyRepository.save(company);

        // Publish event to RabbitMQ
        CompanyEvent event = new CompanyEvent(
                savedCompany.getId(),
                savedCompany.getName(),
                savedCompany.getIndustry(),
                savedCompany.getDescription(),
                savedCompany.getEmail(),
                savedCompany.getLocation(),
                "UPDATED");

        rabbitMQPublisher.publishCompanyEvent(event);

        return savedCompany;
    }

    /**
     * Delete a company and publish an event
     */
    @Transactional
    public void deleteCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow();

        // Publish event to RabbitMQ
        CompanyEvent event = new CompanyEvent(
                company.getId(),
                company.getName(),
                company.getIndustry(),
                company.getDescription(),
                company.getEmail(),
                company.getLocation(),
                "DELETED");

        rabbitMQPublisher.publishCompanyEvent(event);

        companyRepository.deleteById(companyId);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    public List<Company> getCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustry(industry);
    }
}
