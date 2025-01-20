package com.lowtech.webshop.controller;

import com.lowtech.webshop.model.Inventory;
import com.lowtech.webshop.model.Product;
import com.lowtech.webshop.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 15:38
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public Iterable<Inventory> getAllInventories() {
        return inventoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inventory> saveOrUpdateInventory(
            @RequestParam Long productId,
            @RequestParam int quantity) {
        try {
            Inventory inventory = inventoryService.saveOrUpdate(productId, quantity);
            return ResponseEntity.ok(inventory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestParam int quantity) {
        try {
            Inventory updatedInventory = inventoryService.updateInventory(id, quantity);
            return ResponseEntity.ok(updatedInventory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
