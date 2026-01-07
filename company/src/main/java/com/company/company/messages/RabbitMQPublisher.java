package com.company.company.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.company.company.dtos.CompanyEvent;

@Service
public class RabbitMQPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes a CompanyEvent to RabbitMQ
     */

    public void publishCompanyEvent(CompanyEvent event) {
        LOGGER.info("Publishing company event: {} for company: {}",
                event.getEventType(), event.getCompanyName());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.COMPANY_EXCHANGE,
                RabbitMQConfig.COMPANY_ROUTING_KEY,
                event);

        LOGGER.info("Company event published successfully");
    }

}
