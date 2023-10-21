package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}