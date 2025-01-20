package com.lowtech.webshop.repository;

import com.lowtech.webshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 14:15
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}
