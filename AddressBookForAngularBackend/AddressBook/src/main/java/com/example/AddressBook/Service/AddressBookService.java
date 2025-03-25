package com.example.AddressBook.Service;


import com.example.AddressBook.DTO.AddressBookDTO;
import com.example.AddressBook.Interface.AddressBookInterface;
import com.example.AddressBook.Model.Address;
import com.example.AddressBook.Model.User;
import com.example.AddressBook.Repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService implements AddressBookInterface {

    @Autowired
    AddressBookRepository addressBookRepository;

    static final String ADDRESS_CACHE = "addresses";


    @Override
    public Address addAddress(AddressBookDTO addressBookDTO , User user) throws Exception {

        Optional<?> checkformail = addressBookRepository.findByEmail(addressBookDTO.getEmail());

        if (checkformail.isPresent()){
            throw new RuntimeException("Email already present");
        }

        Address temp = new Address();
        temp.setAddress(addressBookDTO.getAddress());
        temp.setName(addressBookDTO.getName());
        temp.setEmail(addressBookDTO.getEmail());
        temp.setPhoneNumber(addressBookDTO.getPhoneNumber());
        temp.setUser(user);
        return addressBookRepository.save(temp);
    }

    @Override
    public void deleteAddress(Long ID) {
         addressBookRepository.deleteById(ID);
    }

    @Override
    public Address updateAddress(Long Id, AddressBookDTO addressBookDTO) throws Exception {
        Address existingAddress = addressBookRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id " + Id));

        if (!existingAddress.getEmail().equals(addressBookDTO.getEmail()) &&
                addressBookRepository.findByEmail(addressBookDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Duplicate contact with email: " + addressBookDTO.getEmail());
        }

        if (!existingAddress.getPhoneNumber().equals(addressBookDTO.getPhoneNumber()) &&
                addressBookRepository.findByPhoneNumber(addressBookDTO.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Duplicate contact with phone number: " + addressBookDTO.getPhoneNumber());
        }

        existingAddress.setName(addressBookDTO.getName());
        existingAddress.setEmail(addressBookDTO.getEmail());
        existingAddress.setPhoneNumber(addressBookDTO.getPhoneNumber());
        existingAddress.setAddress(addressBookDTO.getAddress());

        return addressBookRepository.save(existingAddress);
    }

    @Override
    public List<Address> getAllContacts() {
        return addressBookRepository.findAll();
    }

    @Override

    public Optional<Address> getContactById(Long id) {
        return addressBookRepository.findById(id);
    }

    @Override
    public Optional<Address> getContactByEmail(String email) {
        return addressBookRepository.findByEmail(email);
    }

    @Override
    public Optional<Address> getContactByPhoneNumber(String phoneNumber) {
        return addressBookRepository.findByPhoneNumber(phoneNumber);
    }
}
