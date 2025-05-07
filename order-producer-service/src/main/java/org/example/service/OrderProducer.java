package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 1_000)
    public void sendFakeOrder() {
        String payload = """
                { "orderId": %d, "productId": %d, "quantity": %d }
                """.formatted(System.nanoTime(), (int)(Math.random()*5)+1, (int)(Math.random()*9)+1);

        kafkaTemplate.send("orders", payload);
        System.out.println("[PRODUCER] sent → " + payload);
    }
}
