package com.example.orderservice.client;

import com.example.orderservice.dto.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;


    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDTO getProduct(Long id){
        return restTemplate.getForObject("http://localhost:8080/products/" + id, ProductDTO.class);
    }
    {}
}
