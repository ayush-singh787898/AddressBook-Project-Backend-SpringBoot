package com.example.AddressBook.Interface;

import com.example.AddressBook.DTO.AddressBookDTO;
import com.example.AddressBook.Model.Address;
import com.example.AddressBook.Model.User;

import java.util.List;
import java.util.Optional;

public interface AddressBookInterface {
    
    
    //Method to Add the Address in the address book 
    Address addAddress(AddressBookDTO addressBookDTO , User user) throws Exception;
    
    //Method to Delete The Address form the address Book
    
    void deleteAddress(Long ID);

    //update the address
    Address updateAddress(Long Id , AddressBookDTO addressBookDTO) throws Exception;

    //reading operations
    List<Address> getAllContacts();
    Optional<Address> getContactById(Long id);
    Optional<Address> getContactByEmail(String email);
    Optional<Address> getContactByPhoneNumber(String phoneNumber);
    
    
}

