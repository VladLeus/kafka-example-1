package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private static final String TOPIC = "orders";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${order.producer.partition}")
    private int targetPartition;          // 0  or  1

    @Scheduled(fixedRate = 1_000)         // every second
    public void sendFakeOrder() {

        String payload = """
                { "orderId": %d, "productId": %d, "quantity": %d }
                """.formatted(System.nanoTime(),
                (int)(Math.random()*5)+1,
                (int)(Math.random()*9)+1);

        kafkaTemplate.send(
                new ProducerRecord<>(TOPIC, targetPartition, null, payload));

        System.out.printf("[PRODUCER-%d] → partition=%d  %s%n",
                targetPartition,     // id
                targetPartition,     // real partition
                payload);
    }
}
