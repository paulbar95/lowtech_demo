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
import com.lowtech.webshop.service.EmailService;

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
    private final EmailService emailService;

    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository, ProductRepository productRepository,EmailService emailService) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.emailService = emailService;
    }

    /**
     * Create a new order.
     * Checks the stock level and reduces it accordingly.
     * @param customerName The customer name.
     * @param customerEmail The cusomer email.
     * @param order The new order.
     * @return The saved order.
     */
    public Order createOrder(String customerName, String customerEmail,String paymentMethod, List<OrderProductDTO> products) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setStatus(Order.OrderStatus.PENDING);
        // Zahlungsinformationen setzen:
        order.setPaymentMethod(Order.PaymentMethod.valueOf(paymentMethod));;


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
        Order savedOrder = orderRepository.save(order);
        // Bestimme eine aussagekräftige Darstellung der Zahlungsmethode:
        String paymentInfo = savedOrder.getPaymentMethod().toString();


        // E-Mail-Versand vorbereiten
        String subject = "Order Confirmation - Webshop";
        StringBuilder bodyBuilder = new StringBuilder();

        // Grundlegende Bestellinformationen
        bodyBuilder.append(String.format(
                "Dear %s,\n\nThank you for your order!\nOrder ID: %d\nStatus: %s\nPayment Method: %s\n\n",
                order.getCustomerName(),
                savedOrder.getId(),
                savedOrder.getStatus(),
                savedOrder.getPaymentMethod()
        ));

        // Produktdetails hinzufügen
        bodyBuilder.append("Purchased Products:\n");

        // Iteriere über die OrderItems (in savedOrder.getProducts())
        for (OrderItem orderItem : savedOrder.getProducts()) {
            Product product = orderItem.getProduct(); // Hole das zugehörige Produkt

            // Füge die Produktdetails in die E-Mail ein:
            bodyBuilder.append(String.format(
                    "Name: %s\nPrice: %.2f EUR\n\n",
                    product.getName(),
                    product.getPrice() / 100.0));
        }

        bodyBuilder.append("Best regards,\nWebshop Team");

        String body = bodyBuilder.toString();
        emailService.sendEmail(order.getCustomerEmail(), subject, body);

        return savedOrder;
    }

    /**
     * Update the status of an order.
     * @param orderId The ID of the order.
     * @param status The new status.
     * @return The updated order.
     */
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
    /*    return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found"));
    }*/
        Order updatedOrder = orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found"));

        // Nach der Statusaktualisierung eine E-Mail an den Kunden versenden
        String subject = "Order Status Updated - Webshop";
        String body = String.format(
                "Dear %s,\n\nYour order with ID %d has been updated to status: %s.\n\nBest regards,\nWebshop Team",
                updatedOrder.getCustomerName(),
                updatedOrder.getId(),
                updatedOrder.getStatus()
        );
        emailService.sendEmail(updatedOrder.getCustomerEmail(), subject, body);

        return updatedOrder;
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

    /**
     * Rollback inventory changes in case of an exception during order creation.
     *
     * @param products List of products and quantities to restore.
     */
    private void rollbackInventory(List<OrderProductDTO> products) {
        products.forEach(productDTO -> {
            inventoryRepository.findById(productDTO.getProductId()).ifPresent(inventory -> {
                inventory.setQuantity(inventory.getQuantity() + productDTO.getQuantity());
                inventoryRepository.save(inventory);
            });
        });
    }
}
