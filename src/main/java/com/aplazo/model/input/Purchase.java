package com.aplazo.model.input;

public class Purchase {
    private final Long customerId;
    private final Double purchaseAmount; 
    
    public Purchase(Long customerId, Double purchaseAmount) {
        this.customerId = customerId;
        this.purchaseAmount = purchaseAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Double getPurchaseAmount() {
        return purchaseAmount;
    }
}
