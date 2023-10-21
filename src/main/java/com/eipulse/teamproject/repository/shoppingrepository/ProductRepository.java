package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.entity.shoppingentity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}