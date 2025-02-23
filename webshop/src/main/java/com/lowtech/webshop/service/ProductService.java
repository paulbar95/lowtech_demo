package com.lowtech.webshop.service;

import com.lowtech.webshop.model.Product;
import com.lowtech.webshop.repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 14:17
 */
@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class ProductService {

    @Autowired
    private ProductServiceConfig config;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setImageUrl(product.getImageUrl());
                    return productRepository.save(existingProduct);
                }).orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public String getImageUrl() {
        return config.getImageUrl();
    }
}

@Getter
@Setter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "env")
class ProductServiceConfig {
    private String imageUrl;
}