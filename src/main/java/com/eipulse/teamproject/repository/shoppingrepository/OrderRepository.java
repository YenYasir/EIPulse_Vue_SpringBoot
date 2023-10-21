package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.entity.shoppingentity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}