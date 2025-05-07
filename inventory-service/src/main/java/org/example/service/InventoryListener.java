package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryListener {

    @KafkaListener(topics = "orders", groupId = "inventory-service")
    public void adjustStock(String json) {
        System.out.println("[INV] stock adjusted from event " + json);
    }
}
