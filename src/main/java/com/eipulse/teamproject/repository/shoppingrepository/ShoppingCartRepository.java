package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.entity.shoppingentity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    public ShoppingCart findByEmployee_EmpId(Integer empId);
}