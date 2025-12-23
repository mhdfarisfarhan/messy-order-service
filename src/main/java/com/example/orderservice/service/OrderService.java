package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.*;
import com.example.orderservice.entity.Order;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderService {
    private final OrderRepository or;
    private final ProductClient productClient;

    public OrderService(OrderRepository or, ProductClient productClient) {
        this.or = or;
        this.productClient = productClient;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        double total = request.items().stream().mapToDouble(this::calculateItemTotal).sum();

        double discounted = total * (1 - request.customerType().getDiscount());

        if (discounted > 1000) {
            discounted -= 50;
        }

        Order order = new Order();
        order.setCustomerType(request.customerType().toString());
        order.setTotal(discounted);
        order.setStatus(OrderStatus.PENDING.toString());

        or.save(order);

        return new OrderResponseDTO(order.getId(), discounted, order.getStatus());
    }

    private double calculateItemTotal(OrderItemDTO item) {
        ProductDTO product = productClient.getProduct(item.productId());

        if (product.stock() < item.quantity()) {
            throw new IllegalStateException("Not enough stock for the product " + item.productId());
        }

        return product.price() *  item.quantity();
    }

    public OrderResponseDTO getOrder(Long id) throws Exception {
        Order order = or.findById(id).orElseThrow(() -> new Exception("Order not found"));

        return new OrderResponseDTO(order.getId(), order.getTotal(), order.getStatus()
        );
    }

    public void updateStatus(Long orderId, OrderStatus orderStatus) throws Exception {
        Order order = or.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        order.setStatus(orderStatus.toString());
        or.save(order);
    }

    public List<PopularProductDTO> getPopularProducts() {
        List<Order> allOrders = or.findAll();

        // Using HashMap to count products efficiently
        Map<Long, Integer> productCounts = new HashMap<>();

        for (Order order : allOrders) {
            for (String item : order.getItems()) {
                // Simulating product ID extraction
                Long productId = Long.parseLong(item.split(":")[0]);

                // Incrementing the count in the HashMap
                productCounts.put(productId, productCounts.getOrDefault(productId, 0) + 1);
            }
        }

        // Converting the HashMap to a List of PopularProductDTO
        List<PopularProductDTO> popularProducts = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : productCounts.entrySet()) {
            popularProducts.add(new PopularProductDTO(entry.getKey(), entry.getValue()));
        }

        return popularProducts;
    }
}
