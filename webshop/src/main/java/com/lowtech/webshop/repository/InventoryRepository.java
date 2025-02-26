package com.lowtech.webshop.repository;

import com.lowtech.webshop.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 15:25
 */
@RequestMapping
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductId(Long productId);
}
