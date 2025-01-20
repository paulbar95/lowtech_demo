package com.lowtech.webshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 16:09
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public enum OrderStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }

    private String customerName;
    private String customerEmail;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    @JsonManagedReference
    private List<OrderItem> products = new ArrayList<>();
}
