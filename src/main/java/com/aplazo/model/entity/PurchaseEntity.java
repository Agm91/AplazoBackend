package com.aplazo.model.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PurchaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private double purchaseAmount;
    private double commissionAmount;
    private double paymentAmount;
    private LocalDate purchaseDate;
    @ElementCollection
    private List<LocalDate> paymentDates;

    public PurchaseEntity() {}

    public PurchaseEntity(Long customerId, double purchaseAmount, double commissionAmount, double paymentAmount, LocalDate purchaseDate, List<LocalDate> paymentDates) {
        this.customerId = customerId;
        this.purchaseAmount = purchaseAmount;
        this.commissionAmount = commissionAmount;
        this.paymentAmount = paymentAmount;
        this.purchaseDate = purchaseDate;
        this.paymentDates = paymentDates;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<LocalDate> getPaymentDates() {
        return paymentDates;
    }

    public void setPaymentDates(List<LocalDate> paymentDates) {
        this.paymentDates = paymentDates;
    }
}
