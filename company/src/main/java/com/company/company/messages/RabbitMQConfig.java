package com.company.company.messages;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

    public static final String COMPANY_QUEUE = "company.queue";
    public static final String COMPANY_EXCHANGE = "company.exchange";
    public static final String COMPANY_ROUTING_KEY = "company.routing.key";

    /**
     * Creates the queue where messages will be stored
     */

    @Bean
    public Queue companyQueue() {
        return new Queue(COMPANY_QUEUE, true); // durable = true
    }

    /**
     * Creates the exchange where messages will be published
     */

    @Bean
    public TopicExchange companyExchange() {
        return new TopicExchange(COMPANY_EXCHANGE);
    }

    /**
     * Creates the binding between the exchange and the queue
     */

    @Bean
    public Binding binding(Queue companyQueue, TopicExchange companyExchange) {
        return BindingBuilder.bind(companyQueue).to(companyExchange).with(COMPANY_ROUTING_KEY);
    }

    /**
     * Message converter to convert Java objects to JSON
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    /**
     * RabbitTemplate configured with JSON message converter
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
