package org.example;

import org.example.service.OrderProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderProducerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProducer.class, args);
    }
}
