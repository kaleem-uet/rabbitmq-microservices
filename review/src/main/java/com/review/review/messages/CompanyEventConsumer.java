package com.review.review.messages;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.review.review.config.RabbitMQConfig;
import com.review.review.dtos.CompanyEvent;
import com.review.review.entities.CompanyInfo;
import com.review.review.repository.CompanyInfoRepository;

@Service
public class CompanyEventConsumer {

    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CompanyEventConsumer.class);

    private final CompanyInfoRepository companyInfoRepository;

    public CompanyEventConsumer(CompanyInfoRepository companyInfoRepository) {
        this.companyInfoRepository = companyInfoRepository;
    }

    /**
     * Listens to the company queue and processes incoming events
     */
    @RabbitListener(queues = RabbitMQConfig.COMPANY_QUEUE)
    public void consumeCompanyEvent(CompanyEvent event) {
        LOGGER.info("Received company event: {} for company: {}",
                event.getEventType(), event.getCompanyName());

        try {
            switch (event.getEventType()) {
                case "CREATED":
                    handleCompanyCreated(event);
                    break;
                case "UPDATED":
                    handleCompanyUpdated(event);
                    break;
                case "DELETED":
                    handleCompanyDeleted(event);
                    break;
                default:
                    LOGGER.warn("Unknown event type: {}", event.getEventType());
            }

            LOGGER.info("Successfully processed company event for: {}", event.getCompanyName());
        } catch (Exception e) {
            LOGGER.error("Error processing company event", e);
        }
    }

    private void handleCompanyCreated(CompanyEvent event) {
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyId(event.getCompanyId());
        companyInfo.setCompanyName(event.getCompanyName());
        companyInfo.setIndustry(event.getIndustry());
        companyInfo.setDescription(event.getDescription());
        companyInfo.setEmail(event.getEmail());
        companyInfo.setLocation(event.getLocation());
        companyInfo.setStatus("ACTIVE");

        companyInfoRepository.save(companyInfo);
        LOGGER.info("Company info created in Review Service: {}", event.getCompanyName());
    }

    private void handleCompanyUpdated(CompanyEvent event) {
        companyInfoRepository.findById(event.getCompanyId())
                .ifPresentOrElse(
                        companyInfo -> {
                            companyInfo.setCompanyName(event.getCompanyName());
                            companyInfo.setIndustry(event.getIndustry());
                            companyInfo.setDescription(event.getDescription());
                            companyInfo.setEmail(event.getEmail());
                            companyInfo.setLocation(event.getLocation());
                            companyInfoRepository.save(companyInfo);
                            LOGGER.info("Company info updated in Review Service: {}", event.getCompanyName());
                        },
                        () -> {
                            LOGGER.warn("Company not found for update: {}", event.getCompanyId());
                            // Optionally create it if it doesn't exist
                            handleCompanyCreated(event);
                        });
    }

    private void handleCompanyDeleted(CompanyEvent event) {
        companyInfoRepository.findById(event.getCompanyId())
                .ifPresentOrElse(
                        companyInfo -> {
                            companyInfo.setStatus("DELETED");
                            companyInfoRepository.save(companyInfo);
                            LOGGER.info("Company marked as deleted in Review Service: {}", event.getCompanyName());
                        },
                        () -> LOGGER.warn("Company not found for deletion: {}", event.getCompanyId()));
    }
}
