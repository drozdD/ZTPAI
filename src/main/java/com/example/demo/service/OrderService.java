package com.example.demo.service;

import java.util.List;

public class OrderService {

    public double calculateTotal(List<Double> prices) {
        if (prices == null) {
            throw new IllegalArgumentException("Prices list cannot be null");
        }

        return prices.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
