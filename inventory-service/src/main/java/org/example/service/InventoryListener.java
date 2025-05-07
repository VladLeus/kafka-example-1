package org.example.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class InventoryListener {

    private final RestTemplate restTemplate;
    private final Gson gson = new Gson();

    private record OrderEvent(long orderId, long productId, int quantity) {}

    @KafkaListener(topics = "orders", groupId = "inventory-service")
    public void adjustStock(String json) {

        OrderEvent e = gson.fromJson(json, OrderEvent.class);

        // update local stock map here … (omitted for brevity)

        // 🔹 call ProductService (port 8083)
        String url = "http://localhost:8083/products/update" +
                "?productId=" + e.productId() +
                "&quantity="  + e.quantity();
        restTemplate.postForObject(url, null, Void.class);

        System.out.printf("[INV] forwarded productId=%d quantity=%d%n",
                e.productId(), e.quantity());
    }
}
