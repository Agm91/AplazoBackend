package com.aplazo.service;

import java.time.LocalDate;
import java.time.Period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplazo.model.input.Credit;
import com.aplazo.model.input.Customer;
import com.aplazo.model.output.RegisterCustomerResponse;
import com.aplazo.repository.CustomerRepository;
import com.google.common.annotations.VisibleForTesting;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public RegisterCustomerResponse registerCustomer(Customer customer) {
        int age = calculateAge(customer.getBirthDate());
        logger.debug("Calculated age: {}", age);

        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("The service does not accept minors or people over 65 years old.");
        }

        double creditAmount = assignCreditAmount(age);
        Credit credit = new Credit();
        credit.setAmount(creditAmount);

        customer.setCredit(credit);
        customerRepository.save(customer);

        return new RegisterCustomerResponse(customer.getId(), customer.getCredit().getAmount());
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    @VisibleForTesting
    double assignCreditAmount(int age) {
        if (age >= 18 && age < 25) {
            return 3000.0;
        } else if (age >= 25 && age < 30) {
            return 5000.0;
        } else if (age >= 30 && age <= 65) {
            return 8000.0;
        }
        throw new IllegalArgumentException("Invalid age");
    }
}
