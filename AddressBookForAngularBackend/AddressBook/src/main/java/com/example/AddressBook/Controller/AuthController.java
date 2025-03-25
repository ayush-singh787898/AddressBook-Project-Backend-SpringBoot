package com.example.AddressBook.Controller;

import com.example.AddressBook.DTO.*;
import com.example.AddressBook.Interface.AuthenticationInterface;
import com.example.AddressBook.Model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationInterface authenticationService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getUserProfile() {
        return authenticationService.getUserProfile();
    }
    @Operation(summary = "Authenticate user and get JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful",
                            content = @Content(schema = @Schema(implementation = JwtResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            })
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return authenticationService.loginUser(loginRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Authentication failed: " + e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
//        return authenticationService.loginUser(loginRequest);
    }

    @Operation(summary = "Register a new user",
            description = "Registers a new user in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Username or email is already taken")
            })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            return authenticationService.registerUser(signUpRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
//        return authenticationService.registerUser(signUpRequest);
    }

    @Operation(summary = "Request a password reset",
            description = "Sends a password reset link to the provided email address if the email exists.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password reset link sent successfully",
                            content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User with provided email not found") // Consider if you want to expose this
            })
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            return authenticationService.forgotPassword(forgotPasswordRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Forgot password request failed: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return authenticationService.forgotPassword(forgotPasswordRequest);
    }

    @Operation(summary = "Reset user password",
            description = "Resets the user's password using the provided token and new password.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password reset successfully",
                            content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired reset token")
            })
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            return authenticationService.resetPassword(resetPasswordRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Password reset failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
//        return authenticationService.resetPassword(resetPasswordRequest);
    }

}