package com.aplazo.controller;

import com.aplazo.model.input.Purchase;
import com.aplazo.model.entity.PurchaseEntity;
import com.aplazo.model.input.Customer;
import com.aplazo.model.output.RegisterPurchaseResponse;
import com.aplazo.service.PurchaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPurchase(@RequestBody Purchase purchase) {
        logger.info("Registering purchase: customerID: {}; purchaseAmount: {}", purchase.toString());
    
        Customer customer = purchaseService.getCustomerById(purchase.getCustomerId()).orElse(null);

        if (customer == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }

        if (purchaseService.isPurchaseAmountExceedingCredit(customer, purchase.getPurchaseAmount())) {
            return new ResponseEntity<>("Purchase amount exceeds credit line", HttpStatus.BAD_REQUEST);
        }

        try {
            PurchaseEntity purchaseEntity = purchaseService.registerPurchase(purchase.getCustomerId(), purchase.getPurchaseAmount());
            return new ResponseEntity<>(new RegisterPurchaseResponse(purchaseEntity.getId()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
