package org.example;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InventoryManager {

    private final Map<String, Long> inventory = new ConcurrentHashMap<>();

    public InventoryManager() {
        inventory.put("1", 50L);
        inventory.put("2", 50L);
    }

    public Map<String, Long> getInventory() {
        return inventory;
    }

    public void updateInventory(String productId, long orderQuantity) {
        // Deduct the ordered quantity.
        inventory.computeIfPresent(productId, (k, v) -> v - orderQuantity);
        System.out.println("Updated inventory for product " + productId + ": " + inventory.get(productId));
    }
}
