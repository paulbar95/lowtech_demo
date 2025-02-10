package com.lowtech.webshop.controller;

import com.lowtech.webshop.model.Order;
import com.lowtech.webshop.payload.OrderProductDTO;
import com.lowtech.webshop.payload.OrderRequestDTO;
import com.lowtech.webshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 16:20
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST: Create new Order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            Order order = orderService.createOrder(
                    orderRequest.getCustomerName(),
                    orderRequest.getCustomerEmail(),
                    orderRequest.getPaymentMethod(),
                    orderRequest.getProducts()
            );
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // GET: Get order by Id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Request all Orders
    @GetMapping
    public Iterable<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    // PUT: Update Order status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
