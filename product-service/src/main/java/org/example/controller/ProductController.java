package org.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping("/update")
    public void updateStock(@RequestParam long productId,
                            @RequestParam int quantity) {
        System.out.printf("[PRODUCT-SERVICE] productId=%d quantity=%d%n",
                productId, quantity);
        // pretend we updated the in-memory DB
    }
}
