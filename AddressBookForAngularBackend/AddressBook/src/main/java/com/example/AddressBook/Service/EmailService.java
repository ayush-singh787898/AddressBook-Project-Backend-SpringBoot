package com.example.AddressBook.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationConfirmationEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Registration Successful!");
        message.setText("Dear " + username + ",\n\n" +
                "Thank you for registering with our Address Book application!\n\n" +
                "You can now log in and start managing your contacts.\n\n" +
                "Best regards,\n" +
                "The Address Book Team");

        mailSender.send(message);
    }

    public void sendLoginConfirmationEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Login Confirmation");
        message.setText("Dear " + username + ",\n\n" +
                "You have successfully logged into your Address Book account on " + java.time.LocalDateTime.now() + " IST.\n\n" +
                "If you did not perform this login, please contact us immediately.\n\n" +
                "Best regards,\n" +
                "The Address Book Team");

        mailSender.send(message);
    }
    public void sendForgotPasswordEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText("You have requested to reset your password.\n\n" +
                "Please click on the following link to reset your password:\n" +
                resetLink + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The Address Book Team");

        mailSender.send(message);
    }
}
