package com.example.AddressBook.Interface;

import com.example.AddressBook.DTO.ForgotPasswordRequest;
import com.example.AddressBook.DTO.LoginRequest;
import com.example.AddressBook.DTO.ResetPasswordRequest;
import com.example.AddressBook.DTO.SignupRequest;
import com.example.AddressBook.Model.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationInterface {
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
    ResponseEntity<User> getUserProfile(); // New method to get user profile
    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest);
    }
