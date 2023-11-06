package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.entity.shoppingentity.SubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubTypeRepository extends JpaRepository<SubType, Integer> {

    @Query("select s from SubType s where s.productType.id = :productId")
    public List<SubType>findByProductIdWithSubType(@Param("productId")Integer productId);
}