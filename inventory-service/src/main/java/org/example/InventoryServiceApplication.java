package org.example;

import org.example.InventoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Autowired
    private RestTemplate restTemplate;

    // Listen on the "inventory" topic.
    @KafkaListener(topics = "inventory", groupId = "inventory-service-group")
    public void listenInventory(String message) {
        InventoryEvent event = InventoryEvent.GSON.fromJson(message, InventoryEvent.class);
        System.out.println("Received inventory event: " + event);
        // Invoke the Product Service REST endpoint to update the product inventory.
        String url = "http://localhost:8081/products/update?productId=" + event.productId() +
                "&quantity=" + event.quantity();
        restTemplate.postForEntity(url, null, Void.class);
        System.out.println("Called Product Service to update product: " + event.productId());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
