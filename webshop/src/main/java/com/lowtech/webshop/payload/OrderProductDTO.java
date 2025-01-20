package com.lowtech.webshop.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 17:24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    private Long productId;
    private int quantity;
}
