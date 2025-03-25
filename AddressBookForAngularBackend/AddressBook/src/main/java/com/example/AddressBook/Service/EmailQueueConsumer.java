package com.example.AddressBook.Service;

import com.example.AddressBook.Config.RabbitMQConfig;
import com.example.AddressBook.Message.ForgotPasswordEmailMessage;
import com.example.AddressBook.Message.LoginEmailMessage;
import com.example.AddressBook.Message.RegistrationEmailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailQueueConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.REGISTRATION_QUEUE)
    public void handleRegistrationEmail(RegistrationEmailMessage message) {
        emailService.sendRegistrationConfirmationEmail(message.getToEmail(), message.getUsername());
    }

    @RabbitListener(queues = RabbitMQConfig.LOGIN_QUEUE)
    public void handleLoginEmail(LoginEmailMessage message) {
        emailService.sendLoginConfirmationEmail(message.getToEmail(), message.getUsername());
    }

    @RabbitListener(queues = RabbitMQConfig.FORGOT_PASSWORD_QUEUE)
    public void handleForgotPasswordEmail(ForgotPasswordEmailMessage message) {
        emailService.sendForgotPasswordEmail(message.getToEmail(), message.getResetLink());
    }
}