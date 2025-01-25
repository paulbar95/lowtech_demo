package com.lowtech.webshop.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 14:15
 */
@Entity
@Getter
@Setter
public class Product extends AbstractEntity {

    private String name;
    private String description;

    // cents
    private int price;

    private String category;
    private String imageUrl;
}
