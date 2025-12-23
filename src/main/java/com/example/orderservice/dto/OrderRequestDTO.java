package com.example.orderservice.dto;

import com.example.orderservice.enums.CustomerType;

import java.util.List;

public record OrderRequestDTO(CustomerType customerType, List<OrderItemDTO> items) {}
