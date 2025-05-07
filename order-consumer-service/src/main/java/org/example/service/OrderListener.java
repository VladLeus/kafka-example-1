package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderListener {

    @KafkaListener(topics = "orders", groupId = "order-consumers")
    public void handle(ConsumerRecord<String, String> cr) {
        System.out.printf("[CONS %s] partition=%d offset=%d value=%s%n",
                Thread.currentThread().getName(),
                cr.partition(), cr.offset(), cr.value());
    }
}
