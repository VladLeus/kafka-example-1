package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@EnableScheduling
public class OrderProducerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProducerServiceApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final Random rnd = new Random();

    @Scheduled(fixedDelay = 2000)
    public void sendOrder() {
        // Create a random order placed event.
        OrderPlacedEvent event = new OrderPlacedEvent(
                UUID.randomUUID().toString(),
                "customer1",
                rnd.nextBoolean() ? "1" : "2",
                rnd.nextInt(1, 10)
        );
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("orders", event.toJson());

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to send order: " + ex.getMessage());
            } else {
                System.out.println("Order sent: " + event);
            }
        });

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
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String,String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
