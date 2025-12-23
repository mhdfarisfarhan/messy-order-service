package com.example.orderservice.dto;

public record OrderResponseDTO(Long orderId, double orderTotal, String status) {
}
