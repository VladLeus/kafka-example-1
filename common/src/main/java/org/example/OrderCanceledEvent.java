package org.example;

public record OrderCanceledEvent(String orderId, String customerId, String productId) implements Event {}
