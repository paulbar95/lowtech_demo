package com.lowtech.webshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 16:32
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    private Product product;

    private int quantity;
}
