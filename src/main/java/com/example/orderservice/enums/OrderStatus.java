package com.example.orderservice.enums;

public enum OrderStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private String value;
    OrderStatus(String value) {
        this.value = value;
    }

}
