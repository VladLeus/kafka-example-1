package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final InventoryManager inventoryManager;

    public ProductController(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @GetMapping
    public Map<String, Long> getProducts() {
        return inventoryManager.getInventory();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestParam String productId,
                                              @RequestParam long quantity) {
        inventoryManager.updateInventory(productId, quantity);
        return ResponseEntity.ok().build();
    }
}
