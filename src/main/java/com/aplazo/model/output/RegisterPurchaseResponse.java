package com.aplazo.model.output;

public class RegisterPurchaseResponse {
    private final Long purchaseId;
    
    public RegisterPurchaseResponse(Long id) {
        this.purchaseId = id;
    }

    public Long getId() {
        return purchaseId;
    }
}
