package com.example.AddressBook.Repository;

import com.example.AddressBook.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.addresses WHERE u.username = :username")
    Optional<User> findByUsernameWithAddresses(String username);
}
