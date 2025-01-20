package com.lowtech.webshop.service;

import com.lowtech.webshop.model.OrderItem;
import com.lowtech.webshop.model.Product;
import com.lowtech.webshop.payload.OrderProductDTO;
import com.lowtech.webshop.repository.InventoryRepository;
import com.lowtech.webshop.repository.OrderRepository;
import com.lowtech.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.lowtech.webshop.model.Order;
import com.lowtech.webshop.model.Inventory;

import java.util.List;
import java.util.Optional;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 16:16
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * Create a new order.
     * Checks the stock level and reduces it accordingly.
     * @param order The new order.
     * @return The saved order.
     */
    public Order createOrder(String customerName, String customerEmail, List<OrderProductDTO> products) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setStatus(Order.OrderStatus.PENDING);

        List<OrderItem> orderItems = products.stream().map(productDTO -> {
            Product product = productRepository.findById(productDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productDTO.getProductId()));

            if (productDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero for product ID: " + productDTO.getProductId());
            }

            // Check inventory
            Inventory inventory = inventoryRepository.findById(product.getId())
                    .orElseThrow(() -> new IllegalArgumentException("No inventory found for product: " + product.getName()));

            if (inventory.getQuantity() < productDTO.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            // Reduce inventory
            inventory.setQuantity(inventory.getQuantity() - productDTO.getQuantity());
            inventoryRepository.save(inventory);

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(productDTO.getQuantity());
            return orderItem;
        }).toList();

        order.setProducts(orderItems);
        return orderRepository.save(order);
    }

    /**
     * Update the status of an order.
     * @param orderId The ID of the order.
     * @param status The new status.
     * @return The updated order.
     */
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found"));
    }

    /**
     * Retrieve an order by ID.
     * @param orderId The ID of the order.
     * @return The order.
     */
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    /**
     * Retrieve all orders.
     * @return list of all orders.
     */
    public Iterable<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
