package com.lowtech.webshop.repository;

import com.lowtech.webshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 16:15
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
