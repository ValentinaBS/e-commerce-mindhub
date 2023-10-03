package com.purity.ecommerce.dtos;

public class PaymentRequestDTO {

    private Long amount;
    private String PaymentMethodId;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(Long amount, String paymentMethodId) {
        this.amount = amount;
        PaymentMethodId = paymentMethodId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPaymentMethodId() {
        return PaymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        PaymentMethodId = paymentMethodId;
    }
}
