package com.example.UserService.Controller;

import java.util.Optional;

import com.example.UserService.Entity.CustomerEntity;
import com.example.UserService.Repository.CustomerRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import io.micrometer.observation.annotation.Observed;

@RestController
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * fetching user details from email id
     * @param email
     * @return customer details
     */
    @ApiResponse(responseCode = "200", description = "Get user details using email Id,",
            content = { @Content(mediaType =  "application/json",
            schema = @Schema(implementation = CustomerEntity.class))})
    @GetMapping("/getUserByMail/{email}")
    @Observed(name="get.userByEmail")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<CustomerEntity> byEmail = customerRepo.findByEmail(email);
        if (byEmail.isEmpty()) {
            LOGGER.warn("User with the email {} is not present", email);
            return ResponseEntity.ok("null");
        } else {
            System.out.println(byEmail.get().getEmail());
            LOGGER.info("fetching user details from email id");
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(byEmail.get().getEmail());
        }
    }

    /**
     * Fetching customer details by contact number...
     * @param contactNumber
     * @return customer details
     */
    @ApiResponse(responseCode = "200", description = "Get user details using contact number",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = CustomerEntity.class))})
    @GetMapping("/getUserByContactNumber/{contactNumber}")
    @Observed(name="get.userByContactNumber")
    public ResponseEntity<?> getUserByContactNumber(@PathVariable String contactNumber) {
        Optional<CustomerEntity> user = customerRepo.findByContactNumber(contactNumber);
        if (user.isEmpty()) {
            LOGGER.warn("User with the contact number {} is not present", contactNumber);
            return ResponseEntity.ok("null");
        } else {
            System.out.println(user.get().getContactNumber());
            LOGGER.info("Fetching customer details by contact number...");
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(user.get().getContactNumber());
        }
    }

    /**
     * Fetching customer details by name...
     * @param name
     * @return customer details
     */
    @ApiResponse(responseCode = "200", description = "Get user details using Full Name",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = CustomerEntity.class))})
    @GetMapping("/api/getUserByName/{name}")
    @Observed(name="get.userByName")
    public ResponseEntity<?> getUserName(@PathVariable String name) {
        Optional<CustomerEntity> user = customerRepo.findByFullName(name);
        if (user.isEmpty()) {
            LOGGER.warn("Customer with this username is not available......");
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("null");
        } else {
            System.out.println(user.get().getFullName());

            LOGGER.info("Fetching customer details by name...");
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(user.get().getFullName());
        }
    }

    /**
     * updated user data
     * @param fullName
     * @param email
     * @param contactNumber
     */

    @ApiResponse(responseCode = "201", description = "update user details using id",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = CustomerEntity.class))})
    @PutMapping("/updateUserData/{fullName}/{email}/{contactNumber}")
    @Observed(name="update.userDetails")
    public void updateUser(@PathVariable String fullName, @PathVariable String email,
                           @PathVariable String contactNumber) {
        CustomerEntity customer = customerRepo.findByEmail(email).get();
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setContactNumber(contactNumber);
        customerRepo.save(customer);
        LOGGER.info("Successfully updated user data");
    }

    /**
     * Password reset is done using email Id
     * @param email
     * @param password
     * @return sttus
     */

    @ApiResponse(responseCode = "200", description = "Get password using email Id,",
            content = { @Content(mediaType =  "application/json",
                    schema = @Schema(implementation = CustomerEntity.class))})
    @PatchMapping("/updatePasswordByEmail/{email}/{password}")
    @Observed(name="update.passwordByEmail")
    public ResponseEntity<?> updatePasswordByEmail(@PathVariable String email, @PathVariable String password) {

        CustomerEntity customer = customerRepo.findByEmail(email).get();
        customer.setPassword(encoder.encode(password));
        customerRepo.save(customer);

        LOGGER.info("Password reset is done using email Id");
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("updated password");

    }

}