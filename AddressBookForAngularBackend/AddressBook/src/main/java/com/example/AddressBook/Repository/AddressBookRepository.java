package com.example.AddressBook.Repository;

import com.example.AddressBook.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressBookRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByEmail(String email);
    Optional<Address> findByPhoneNumber(String PhoneNumber);
    List<Address> findByUser_Username(String username); // Find addresses by username

}
