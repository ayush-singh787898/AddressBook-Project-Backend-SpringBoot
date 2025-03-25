package com.example.AddressBook.DTO;


import com.example.AddressBook.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AddressBookDTO {

        private Long id;
        @NotNull(message = "Name is mandatory")
        private String name;

        @NotNull(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        private String email;

        private String phoneNumber;
        private String address;
        private User user;
        public AddressBookDTO(String testName, String mail, String number, String testAddress) {
        }
}
