package com.example.orderservice.enums;

public enum CustomerType {
    VIP(0.15),
    PREMIUM(0.1),
    REGULAR(0.05),
    NEW(0.02),
    NONE(0.0);

    private final double discount;
    CustomerType(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
