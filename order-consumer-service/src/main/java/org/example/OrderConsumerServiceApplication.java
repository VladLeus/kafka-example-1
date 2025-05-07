package org.example;

import org.example.service.OrderListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderListener.class, args);
    }
}
