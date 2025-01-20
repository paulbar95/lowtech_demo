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
}
