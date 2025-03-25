package com.example.AddressBook.Service;

import com.example.AddressBook.Config.JwtUtils;
import com.example.AddressBook.DTO.AddressBookDTO;
import com.example.AddressBook.Model.Address;
import com.example.AddressBook.Model.User;
import com.example.AddressBook.Repository.AddressBookRepository;
import com.example.AddressBook.Config.JwtUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    class AddressBookServiceTest {

        @Mock
        private AddressBookRepository addressBookRepository;

        @InjectMocks
        private AddressBookService addressBookService;

        @Mock
        private JwtUtils jwtUtil;

        private Address address;
        private AddressBookDTO addressBookDTO;
        private User user;
        private String token;

        @BeforeEach
        void setUp() {
            user = new User();
            user.setId(1L);

            address = new Address();
            address.setId(1L);
            address.setName("John Doe");
            address.setEmail("john@example.com");
            address.setPhoneNumber("1234567890");
            address.setAddress("123 Main Street");
            address.setUser(user);

            addressBookDTO = new AddressBookDTO();
            addressBookDTO.setName("John Doe");
            addressBookDTO.setEmail("john@example.com");
            addressBookDTO.setPhoneNumber("1234567890");
            addressBookDTO.setAddress("123 Main Street");

            token = jwtUtil.generateJwtToken(null);
        }

        // ✅ ADD ADDRESS TEST CASES

        @Test
        void testAddAddress_Success() throws Exception {
            when(addressBookRepository.findByEmail(addressBookDTO.getEmail())).thenReturn(Optional.empty());
            when(addressBookRepository.save(any(Address.class))).thenReturn(address);

            Address result = addressBookService.addAddress(addressBookDTO, user);

            assertTrue(result.getEmail().equals("john@example.com"));
        }


        @Test
        void testAddAddress_Failure() {
            when(addressBookRepository.findByEmail(addressBookDTO.getEmail())).thenReturn(Optional.of(address));

            assertFalse(addressBookRepository.findByEmail(addressBookDTO.getEmail()).isEmpty());
        }

        @Test
        void testAddAddress_ThrowsException() {
            when(addressBookRepository.findByEmail(addressBookDTO.getEmail())).thenReturn(Optional.of(address));

            assertThrows(RuntimeException.class, () -> {
                addressBookService.addAddress(addressBookDTO, user);
            });
        }

        // ✅ DELETE ADDRESS TEST CASES

        @Test
        void testDeleteAddress_Success() {
            doNothing().when(addressBookRepository).deleteById(1L);

            addressBookService.deleteAddress(1L);

            verify(addressBookRepository, times(1)).deleteById(1L);
            assertTrue(true);
        }

        @Test
        void testDeleteAddress_Failure() {
            doThrow(new RuntimeException("Address not found")).when(addressBookRepository).deleteById(2L);

            assertThrows(RuntimeException.class, () -> {
                addressBookService.deleteAddress(2L);
            });
        }

        @Test
        void testDeleteAddress_ThrowsException() {
            doThrow(new RuntimeException("Failed to delete")).when(addressBookRepository).deleteById(1L);

            assertThrows(RuntimeException.class, () -> {
                addressBookService.deleteAddress(1L);
            });
        }

        // ✅ UPDATE ADDRESS TEST CASES

        @Test
        void testUpdateAddress_Success() throws Exception {
            when(addressBookRepository.findById(1L)).thenReturn(Optional.of(address));
            when(addressBookRepository.save(any(Address.class))).thenReturn(address);

            Address result = addressBookService.updateAddress(1L, addressBookDTO);

            assertTrue(result.getEmail().equals("john@example.com"));
        }

        @Test
        void testUpdateAddress_Failure() {
            when(addressBookRepository.findById(2L)).thenReturn(Optional.empty());

            assertFalse(addressBookRepository.findById(2L).isPresent());
        }

        @Test
        void testUpdateAddress_ThrowsException() {
            when(addressBookRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> {
                addressBookService.updateAddress(1L, addressBookDTO);
            });
        }

        // ✅ GET ALL CONTACTS TEST CASES

        @Test
        void testGetAllContacts_Success() {
            when(addressBookRepository.findAll()).thenReturn(Arrays.asList(address));

            List<Address> result = addressBookService.getAllContacts();

            assertTrue(result.size() > 0);
        }

        @Test
        void testGetAllContacts_Failure() {
            when(addressBookRepository.findAll()).thenReturn(Arrays.asList());

            List<Address> result = addressBookService.getAllContacts();

            assertFalse(result.size() > 0);
        }

        @Test
        void testGetAllContacts_ThrowsException() {
            when(addressBookRepository.findAll()).thenThrow(new RuntimeException("Error fetching data"));

            assertThrows(RuntimeException.class, () -> {
                addressBookService.getAllContacts();
            });
        }

        // ✅ GET CONTACT BY ID TEST CASES

        @Test
        void testGetContactById_Success() {
            when(addressBookRepository.findById(1L)).thenReturn(Optional.of(address));

            Optional<Address> result = addressBookService.getContactById(1L);

            assertTrue(result.isPresent());
        }

        @Test
        void testGetContactById_Failure() {
            when(addressBookRepository.findById(2L)).thenReturn(Optional.empty());

            Optional<Address> result = addressBookService.getContactById(2L);

            assertFalse(result.isPresent());
        }

        @Test
        void testGetContactById_ThrowsException() {
            when(addressBookRepository.findById(1L)).thenThrow(new RuntimeException("Contact not found"));

            assertThrows(RuntimeException.class, () -> {
                addressBookService.getContactById(1L);
            });
        }

}
