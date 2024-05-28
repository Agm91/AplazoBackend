package com.aplazo.service;

import com.aplazo.model.entity.PurchaseEntity;
import com.aplazo.model.input.Customer;
import com.aplazo.repository.CustomerRepository;
import com.aplazo.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private static final double SCHEME1_RATE = 0.13;
    private static final double SCHEME2_RATE = 0.16;
    private static final int PAYMENTS_NUMBER = 5;
    private static final int PAYMENT_FREQUENCY_DAYS = 15;

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public boolean isPurchaseAmountExceedingCredit(Customer customer, double purchaseAmount) {
        return customer.getCredit() == null || purchaseAmount > customer.getCredit().getAmount();
    }

    public double determineSchemeRate(Customer customer) {
        if (customer.getName().startsWith("C") || customer.getName().startsWith("L") || customer.getName().startsWith("H")) {
            return SCHEME1_RATE;
        }
        if (customer.getId() > 25) {
            return SCHEME2_RATE;
        }
        return SCHEME2_RATE; // Default scheme
    }

    public List<LocalDate> calculatePaymentDates(LocalDate startDate) {
        List<LocalDate> paymentDates = new ArrayList<>();
        for (int i = 1; i <= PAYMENTS_NUMBER; i++) {
            paymentDates.add(startDate.plusDays(i * PAYMENT_FREQUENCY_DAYS));
        }
        return paymentDates;
    }

    public PurchaseEntity registerPurchase(Long customerId, double purchaseAmount) {
        Customer customer = getCustomerById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        double schemeRate = determineSchemeRate(customer);
        double commissionAmount = purchaseAmount * schemeRate;
        double paymentAmount = (purchaseAmount + commissionAmount) / PAYMENTS_NUMBER;

        List<LocalDate> paymentDates = calculatePaymentDates(LocalDate.now());

        PurchaseEntity purchase = new PurchaseEntity(customerId, purchaseAmount, commissionAmount, paymentAmount, LocalDate.now(), paymentDates);
        return purchaseRepository.save(purchase);
    }
}
