package org.example;

public record InventoryEvent(String productId, int quantity) implements Event {}
