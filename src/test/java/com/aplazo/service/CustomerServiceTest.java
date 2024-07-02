package com.aplazo.service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.aplazo.model.input.Customer;
import com.aplazo.model.output.RegisterCustomerResponse;
import com.aplazo.repository.CustomerRepository;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterCustomerAgeLessThan18() {
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.now().minusYears(17));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.registerCustomer(customer);
        });

        assertEquals("The service does not accept minors or people over 65 years old.", exception.getMessage());
    }

    @Test
    public void testRegisterCustomerAgeGreaterThan65() {
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.now().minusYears(66));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.registerCustomer(customer);
        });

        assertEquals("The service does not accept minors or people over 65 years old.", exception.getMessage());
    }

    @Test
    public void testRegisterCustomerValidAge() {
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.now().minusYears(30));

        RegisterCustomerResponse response = customerService.registerCustomer(customer);

        assertNotNull(response);
        assertEquals(8000.0, response.getAmount());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testAssignCreditAmountAgeBetween18And25() {
        double creditAmount = customerService.assignCreditAmount(20);
        assertEquals(3000.0, creditAmount);
    }

    @Test
    public void testAssignCreditAmountAgeBetween25And30() {
        double creditAmount = customerService.assignCreditAmount(27);
        assertEquals(5000.0, creditAmount);
    }

    @Test
    public void testAssignCreditAmountAgeBetween30And65() {
        double creditAmount = customerService.assignCreditAmount(40);
        assertEquals(8000.0, creditAmount);
    }

    @Test
    public void testAssignCreditAmountInvalidAge() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.assignCreditAmount(70);
        });

        assertEquals("Invalid age", exception.getMessage());
    }
}