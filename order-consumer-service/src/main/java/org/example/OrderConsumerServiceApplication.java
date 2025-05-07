package org.example;

import org.example.service.OrderListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OrderConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderConsumerServiceApplication.class, args);
    }
}
