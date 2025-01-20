package com.lowtech.webshop.service;

import com.lowtech.webshop.model.Inventory;
import com.lowtech.webshop.model.Product;
import com.lowtech.webshop.repository.InventoryRepository;
import com.lowtech.webshop.repository.ProductRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 15:26
 */
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * Retrieve stock using the ID.
     * @param inventoryId The ID of the stock.
     * @return Optional with the inventory.
     */
    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    /**
     * Retrieve the stock of a product.
     * @param product The product whose stock is to be checked.
     * Optional with the inventory of the product.
     */
    public Optional<Inventory> findByProduct(Product product) {
        return inventoryRepository.findAll()
                .stream()
                .filter(inventory -> inventory.getProduct().equals(product))
                .findFirst();
    }

    public Inventory saveOrUpdate(Long productId, int quantity) {
        // Produkt aus der Datenbank holen
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // Nach bestehendem Inventory suchen
        Optional<Inventory> existingInventory = inventoryRepository.findAll()
                .stream()
                .filter(inventory -> inventory.getProduct().equals(product))
                .findFirst();

        if (existingInventory.isPresent()) {
            Inventory inventory = existingInventory.get();
            inventory.setQuantity(quantity);
            return inventoryRepository.save(inventory);
        } else {
            Inventory newInventory = new Inventory();
            newInventory.setProduct(product);
            newInventory.setQuantity(quantity);
            return inventoryRepository.save(newInventory);
        }
    }
//    public Inventory saveOrUpdate(Product product, int quantity) {
//        Optional<Inventory> existingInventory = findByProduct(product);
//
//        if (quantity < 0) {
//            throw new IllegalArgumentException("Inventory quantity cannot be negative.");
//        }
//        if (existingInventory.isPresent()) {
//            Inventory inventory = existingInventory.get();
//            inventory.setQuantity(quantity);
//            return inventoryRepository.save(inventory);
//        } else {
//            Inventory newInventory = new Inventory();
//            newInventory.setProduct(product);
//            newInventory.setQuantity(quantity);
//            return inventoryRepository.save(newInventory);
//        }
//    }

    /**
     * Update stock directly.
     * @param inventoryId The ID of the stock.
     * @param quantity The new quantity.
     * @return The updated inventory.
     */
    public Inventory updateInventory(Long inventoryId, int quantity) {
        return inventoryRepository.findById(inventoryId)
                .map(inventory -> {
                    if (quantity < 0) {
                        throw new IllegalArgumentException("Inventory quantity cannot be negative.");
                    }
                    inventory.setQuantity(quantity);
                    return inventoryRepository.save(inventory);
                })
                .orElseThrow(() -> new IllegalArgumentException("Inventory with ID " + inventoryId + " not found"));
    }

    /**
     * Retrieve total stock.
     * @return list of all inventory entries.
     */
    public Iterable<Inventory> findAll() {
        return inventoryRepository.findAll();
    }
}
