package com.example.orderservice.dto;

import com.example.orderservice.enums.OrderStatus;

public record UpdateStatusDTO(OrderStatus status) {
}
