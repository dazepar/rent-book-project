package com.example.primaryproject.Model;

public class PaymentInfo {
    private String paymentMethod;
    private String deliveryAddress;

    public PaymentInfo() {}

    public PaymentInfo(String paymentMethod, String deliveryAddress) {
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
