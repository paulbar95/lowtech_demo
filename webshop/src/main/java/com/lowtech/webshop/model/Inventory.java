package com.lowtech.webshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 15:21
 */
@Entity
@Getter
@Setter
public class Inventory extends AbstractEntity {

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;
}
