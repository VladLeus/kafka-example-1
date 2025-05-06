package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class OrderConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderConsumerServiceApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "orders", groupId = "order-consumer-group")
    public void listenOrder(String message) {
        OrderPlacedEvent orderEvent = OrderPlacedEvent.GSON.fromJson(message, OrderPlacedEvent.class);
        // Simulate processing with 70% success rate.
        boolean success = Math.random() > 0.3;
        if (success) {
            // On success, send an inventory event indicating the order quantity to deduct.
            InventoryEvent inventoryEvent = new InventoryEvent(orderEvent.productId(), orderEvent.quantity());
            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send("inventory", inventoryEvent.toJson());

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    System.err.println("Failed to send inventory event: " + ex.getMessage());
                } else {
                    System.out.println("Order processed successfully, inventory event sent: " + inventoryEvent);
                }
            });

        } else {
            System.out.println("Order processing failed for order: " + orderEvent.orderId());
        }
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf) {
        return new KafkaTemplate<>(pf);
    }
}
