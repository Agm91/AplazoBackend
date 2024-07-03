package com.aplazo.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.aplazo.model.entity.PurchaseEntity;
import com.aplazo.model.input.Credit;
import com.aplazo.model.input.Customer;
import com.aplazo.repository.CustomerRepository;
import com.aplazo.repository.PurchaseRepository;

@SpringBootTest
@ActiveProfiles("test")
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> result = purchaseService.getCustomerById(customerId);
        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getId());
    }

    @Test
    public void testIsPurchaseAmountExceedingCredit() {
        Customer customer = new Customer();
        Credit credit = new Credit();
        credit.setAmount(1000.0);
        customer.setCredit(credit);

        assertTrue(purchaseService.isPurchaseAmountExceedingCredit(customer, 1500.0));
        assertFalse(purchaseService.isPurchaseAmountExceedingCredit(customer, 500.0));
    }

    @Test
    public void testDetermineSchemeRate() {
        Customer customer1 = new Customer();
        customer1.setName("Carlos");
        Customer customer2 = new Customer();
        customer2.setId(30L);
        customer2.setName("Ana");
        Customer customer3 = new Customer();
        customer3.setId(10L);
        customer3.setName("Bob");

        assertEquals(0.13, purchaseService.determineSchemeRate(customer1));
        assertEquals(0.16, purchaseService.determineSchemeRate(customer2));
        assertEquals(0.16, purchaseService.determineSchemeRate(customer3));
    }

    @Test
    public void testCalculatePaymentDates() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        var expectedDates = Arrays.asList(
                LocalDate.of(2024, 1, 16),
                LocalDate.of(2024, 1, 31),
                LocalDate.of(2024, 2, 15),
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 16)
        );

        List<LocalDate> paymentDates = purchaseService.calculatePaymentDates(startDate);
        assertEquals(expectedDates, paymentDates);
    }

    @Test
    public void testRegisterPurchase() {
        Long customerId = 1L;
        double purchaseAmount = 500.0;

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Carlos");
        Credit credit = new Credit();
        credit.setAmount(1000.0);
        customer.setCredit(credit);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(purchaseRepository.save(any(PurchaseEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseEntity purchase = purchaseService.registerPurchase(customerId, purchaseAmount);
        assertNotNull(purchase);
        assertEquals(customerId, purchase.getCustomerId());
        assertEquals(purchaseAmount, purchase.getPurchaseAmount());
    }
}
