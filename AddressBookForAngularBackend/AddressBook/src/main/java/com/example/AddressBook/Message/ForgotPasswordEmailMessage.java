package com.example.AddressBook.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordEmailMessage {
    private String toEmail;
    private String resetLink;
}