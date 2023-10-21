package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.entity.shoppingentity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {


    @Query("from ProductType p order by p.typeName desc ")
    List<ProductType>findAll();
}