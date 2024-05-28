package com.aplazo.model.output;

public class RegisterCustomerResponse {
    private final Long customerId;
    private final Double creditAmount;
    
    public RegisterCustomerResponse(Long customerId, Double creditAmount) {
        this.customerId = customerId;
        this.creditAmount = creditAmount;
    }

    public Double getAmount() {
        return creditAmount;
    }

    public Long getId() {
        return customerId;
    }
}
