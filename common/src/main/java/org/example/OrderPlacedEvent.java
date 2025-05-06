package org.example;

public record OrderPlacedEvent(String orderId, String customerId, String productId, int quantity) implements Event {}
