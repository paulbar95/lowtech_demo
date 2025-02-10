package com.lowtech.webshop.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 17:32
 */
@Getter
@Setter
public class OrderRequestDTO {
    private String customerName;
    private String customerEmail;
    private List<OrderProductDTO> products;
    private String paymentMethod; // z. B. "PAYPAL" oder "OTHER"

    // Getter & Setter
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public List<OrderProductDTO> getProducts() {
        return products;
    }
    public void setProducts(List<OrderProductDTO> products) {
        this.products = products;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}


