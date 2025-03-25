package com.example.AddressBook.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String REGISTRATION_QUEUE = "registration.email.queue";
    public static final String LOGIN_QUEUE = "login.email.queue";
    public static final String FORGOT_PASSWORD_QUEUE = "forgot.password.email.queue";
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String REGISTRATION_ROUTING_KEY = "registration.email";
    public static final String LOGIN_ROUTING_KEY = "login.email";
    public static final String FORGOT_PASSWORD_ROUTING_KEY = "forgot.password.email";

    @Bean
    public Queue registrationQueue() {
        return new Queue(REGISTRATION_QUEUE, false); // durable is false for simplicity
    }

    @Bean
    public Queue loginQueue() {
        return new Queue(LOGIN_QUEUE, false);
    }

    @Bean
    public Queue forgotPasswordQueue() {
        return new Queue(FORGOT_PASSWORD_QUEUE, false);
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding registrationBinding(Queue registrationQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(registrationQueue).to(emailExchange).with(REGISTRATION_ROUTING_KEY);
    }

    @Bean
    public Binding loginBinding(Queue loginQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(loginQueue).to(emailExchange).with(LOGIN_ROUTING_KEY);
    }

    @Bean
    public Binding forgotPasswordBinding(Queue forgotPasswordQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(forgotPasswordQueue).to(emailExchange).with(FORGOT_PASSWORD_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}