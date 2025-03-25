package com.example.AddressBook.Controller;

import com.example.AddressBook.DTO.AddressBookDTO;
import com.example.AddressBook.DTO.MessageResponse;
import com.example.AddressBook.Model.Address;
import com.example.AddressBook.Model.User;
import com.example.AddressBook.Repository.AddressBookRepository;
import com.example.AddressBook.Repository.UserRepository;
import com.example.AddressBook.Service.AddressBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
@RestController
@RequestMapping("/api")
public class AddressBookController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressBookRepository addressRepository;
    @Autowired
    AddressBookService addressBookService;


    //this will get all Addresses in the AddressBook
    @GetMapping("/admin/addressBook/all")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Address>> getAllContacts() {
        try {
            List<Address> contacts = addressBookService.getAllContacts();
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Get a particular address using the id
    @GetMapping("/admin/addressBook/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Address> getContactByIdAdmin(@PathVariable Long id) {
        try {
            Optional<Address> contact = addressBookService.getContactById(id);
            return contact.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //get the address based on email
    @GetMapping("/admin/addressBook/email/{email}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Address> getContactByEmailAdmin(@PathVariable String email) {
        try {
            Optional<Address> contact = addressBookService.getContactByEmail(email);
            return contact.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //get the address based on phone number
    @GetMapping("/admin/addressBook/phone/{phoneNumber}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Address> getContactByPhoneNumberAdmin(@PathVariable String phoneNumber) {
        try {
            Optional<Address> contact = addressBookService.getContactByPhoneNumber(phoneNumber);
            return contact.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
//
    }

    //remove the address using the id
    @DeleteMapping("/admin/addressBook/RemoveAddress/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAddressAdmin(@PathVariable Long id) {
        try {
            addressBookService.deleteAddress(id);
            return ResponseEntity.ok("Address Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting address");
        }
    }



    //  User  Endpoints --->
//    @GetMapping("/user/addressBook")
////    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public String initialMessageUser() {
//        return "Welcome to the Address Book!!";
//    }

    @GetMapping("/user/addressBook")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Address>> getUserAddresses() {
        try {
            List<Address> addresses = addressRepository.findByUser_Username("ayush");
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
      }

    @GetMapping("/user/addressBook/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Address> getAddressByIdUser(@PathVariable Long id) {
        try {
            Optional<Address> contact = addressBookService.getContactById(id);
            return contact.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PostMapping("/user/addressBook/addAddress")
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressBookDTO addressBookDTO) throws Exception {
        // Manually setting user ID to 2
        User user = new User();
        user.setId(1L); // Assuming user ID is a Long type

        // Pass the user object while adding the address
        Address add = addressBookService.addAddress(addressBookDTO, user);
        return new ResponseEntity<>(add, HttpStatus.CREATED);
    }


    @PutMapping("/user/addressBook/updateAddress/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<String> updateContactUser(@PathVariable Long id, @Valid @RequestBody AddressBookDTO addressBookDTO) {
//        try {
//            addressBookService.updateAddress(id, addressBookDTO);
//            return ResponseEntity.ok("Contact updated successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating contact: " + e.getMessage());
//        }
//
//    }
    public ResponseEntity<Map<String, String>> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressBookDTO addressBookDTO) throws Exception {
        addressBookService.updateAddress(id, addressBookDTO);

        // Return JSON instead of plain text
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contact updated successfully!");

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/user/addressBook/deleteAddress/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteAddress(@PathVariable Long id) {
//        try {
//            // Check if the address exists
//            Optional<Address> address = addressBookService.getContactById(id);
//            // If the address doesn't exist, return 404 Not Found
//            if (address.isEmpty()) {
//                    throw new Exception();
//            }
//            // Proceed with deleting the address
//            addressBookService.deleteAddress(id);
//            return ResponseEntity.ok("Address Deleted Successfully");
//
//        } catch (Exception e) {
//            // Return 500 Internal Server Error if any exception occurs
//            System.out.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting address");
//        }
        addressBookService.deleteAddress(id);

        // Return JSON response instead of plain text
        Map<String, String> response = new HashMap<>();
        response.put("message", "Address Deleted Successfully");

        return ResponseEntity.ok(response);
    }



}
