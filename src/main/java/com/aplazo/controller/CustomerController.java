package com.aplazo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aplazo.model.input.Customer;
import com.aplazo.model.output.RegisterCustomerResponse;
import com.aplazo.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterCustomerResponse> registerCustomer(@RequestBody Customer customer) {
        logger.info("Registering customer: {}", customer);
        try {
            RegisterCustomerResponse registerResponse = customerService.registerCustomer(customer);
            return ResponseEntity.ok(registerResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
