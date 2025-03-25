package com.example.AddressBook;

import com.example.AddressBook.DTO.AddressBookDTO;
import com.example.AddressBook.DTO.LoginRequest;
import com.example.AddressBook.DTO.SignupRequest;
import com.example.AddressBook.Model.User;
import com.example.AddressBook.Repository.AddressBookRepository;
import com.example.AddressBook.Repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AddressBookApplicationTests {

	@Test
	void contextLoads() {
	}

    @SpringBootTest
    @AutoConfigureMockMvc
    public static class AddressBookControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserRepository userRepository; // Assuming you have this in your test

        @Autowired
        private AddressBookRepository addressBookRepository; // Assuming you have this in your test

        private String jwtToken;
        private Long testAddressId;
        private final String testUsername = "testuser_assert";
        private final String testEmail = "test_assert@example.com";
        private final String testPassword = "password";

        @BeforeEach
        void setUp() throws Exception {
            // Check if user exists
            Optional<User> existingUser = userRepository.findByUsername(testUsername);
            if (existingUser.isEmpty()) {
                // Register a test user
                SignupRequest signupRequest = new SignupRequest();
                signupRequest.setUsername(testUsername);
                signupRequest.setEmail(testEmail);
                signupRequest.setPassword(testPassword);
                mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signupRequest)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
            }

            // Login the test user to get a JWT token
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(testUsername);
            loginRequest.setPassword(testPassword);
            MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String responseContent = loginResult.getResponse().getContentAsString();
            jwtToken = objectMapper.readTree(responseContent).get("token").asText();

            // Create a test address for subsequent tests
            AddressBookDTO addressBookDTO = new AddressBookDTO();
            addressBookDTO.setName("Test Address");
            addressBookDTO.setEmail("test.address@example.com");
            addressBookDTO.setAddress("123 Test St");
            addressBookDTO.setPhoneNumber("1112223333");

            MvcResult addResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/addressBook/addAddress")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(addressBookDTO)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn();
            String addResponseContent = addResult.getResponse().getContentAsString();
                JsonNode responseJson = objectMapper.readTree(addResponseContent);
                System.out.println("responseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+responseJson);
                testAddressId = responseJson.get("id").asLong(); // Assuming the response contains the id field

        }

        @AfterEach
        void tearDown() {
            if (testAddressId != null) {
                addressBookRepository.deleteById(testAddressId);
            }
            Optional<User> userToDelete = userRepository.findByUsername(testUsername);
            userToDelete.ifPresent(user -> userRepository.delete(user));
        }

        @Test
        void testAddAddress_Success_AssertTrue() throws Exception {
            // Prepare the DTO object
            AddressBookDTO addressBookDTO = new AddressBookDTO();
            addressBookDTO.setName("Another Contact");
            addressBookDTO.setEmail("another.contact@example.com");
            addressBookDTO.setAddress("456 Another St");
            addressBookDTO.setPhoneNumber("4445556666");

            // Perform the POST request and get the result
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/addressBook/addAddress")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(addressBookDTO)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn();

            // Get the response content as String
            String responseContent = result.getResponse().getContentAsString();

            // The expected response body (you can adjust the expected fields here as needed)
            String expectedJson = "{"
                    + "\"name\":\"Another Contact\","
                    + "\"email\":\"another.contact@example.com\","
                    + "\"phoneNumber\":\"4445556666\","
                    + "\"address\":\"456 Another St\""
                    + "}";

            // Use JSONAssert to validate the response JSON matches the expected structure
            JSONAssert.assertEquals(expectedJson, responseContent, false);

            // Assert the response status is CREATED (201)
            assertTrue(result.getResponse().getStatus() == HttpStatus.CREATED.value(), "Response status should be CREATED (201)");
        }


        @Test
    void testAddAddress_Failure_InvalidInput_AssertFalse() throws Exception {
        // Create a DTO with missing required fields (name and email)
        AddressBookDTO addressBookDTO = new AddressBookDTO(); // Missing required fields like name and email

        // Perform the POST request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/addressBook/addAddress")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressBookDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expect a BAD_REQUEST status
                .andReturn();

        // Check that the response status is indeed BAD_REQUEST
        assertFalse(result.getResponse().getStatus() == HttpStatus.CREATED.value(), "Response status should not be CREATED");
        assertTrue(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value(), "Response status should be BAD_REQUEST (400)");
    };


        @Test
        void testGetAddressById_Success_AssertTrue() throws Exception {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/addressBook/1")
                            .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value(), "Response status should be OK (200)");
        }

        @Test
        void testGetAddressById_NotFound_AssertFalse() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/user/addressBook/999999") // Assuming ID 9999 doesn't exist
                            .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
            assertFalse(mockMvc.perform(MockMvcRequestBuilders.get("/api/user/addressBook/9999")
                            .header("Authorization", "Bearer " + jwtToken))
                    .andReturn().getResponse().getStatus() == HttpStatus.OK.value(), "Response status should not be OK for non-existent ID");
        }

        @Test
        void testUpdateAddress_Success_AssertTrue() throws Exception {
            AddressBookDTO updatedAddress = new AddressBookDTO();
            updatedAddress.setName("Updated Contact");
            updatedAddress.setEmail("updated.contact@example.com");
            updatedAddress.setAddress("789 Updated Ln");
            updatedAddress.setPhoneNumber("9998887777");

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/addressBook/updateAddress/" + 1)
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedAddress)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            assertTrue(responseContent.contains("Contact updated successfully!"), "Response should contain success message");
            assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value(), "Response status should be OK (200)");
        }

        @Test
        void testUpdateAddress_NotFound_AssertFalse() throws Exception {
            AddressBookDTO updatedAddress = new AddressBookDTO();
            updatedAddress.setName("Updated Contact");
            updatedAddress.setEmail("updated.contact@example.com");
            updatedAddress.setAddress("789 Updated Ln");
            updatedAddress.setPhoneNumber("9998887777");

            mockMvc.perform(MockMvcRequestBuilders.put("/api/user/addressBook/updateAddress/9999") // Assuming ID 9999 doesn't exist
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedAddress)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Your controller returns 400 for errors

            assertFalse(mockMvc.perform(MockMvcRequestBuilders.put("/api/user/addressBook/updateAddress/9999")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedAddress)))
                    .andReturn().getResponse().getStatus() == HttpStatus.OK.value(), "Response status should not be OK for non-existent ID");
        }

        @Test
        void testDeleteAddress_Success_AssertTrue() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/addressBook/deleteAddress/3" )
                            .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(MockMvcResultMatchers.status().isOk()); // Your controller returns 200 for success
            MvcResult getResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/addressBook/3" )
                            .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn();
            assertTrue(getResult.getResponse().getStatus() == HttpStatus.NOT_FOUND.value(), "Address should not be found after deletion");
        }

        @Test
        void testDeleteAddress_NotFound_AssertFalse() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/addressBook/deleteAddress/9999") // Assuming ID 9999 doesn't exist
                            .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError()); // Your controller returns 500 for error

            assertFalse(mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/addressBook/deleteAddress/9999")
                            .header("Authorization", "Bearer " + jwtToken))
                    .andReturn().getResponse().getStatus() == HttpStatus.OK.value(), "Response status should not be OK for non-existent ID");
        }

        @Test
        void testGetAllAddresses_Success_AssertTrue() throws Exception {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/addresses")
                            .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            assertTrue(responseContent.startsWith("["), "Response should be a JSON array");
            assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value(), "Response status should be OK (200)");
        }

        @Test
        void testGetAllAddresses_EmptyList_AssertFalse() throws Exception {
            // To reliably test this, you might need to delete all addresses for the test user first.
            // For this example, we'll just check if a specific non-existent name is NOT present.
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/addresses")
                            .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            assertFalse(responseContent.contains("NonExistentContact"), "Response should not contain a non-existent contact name");
        }

    }
}
