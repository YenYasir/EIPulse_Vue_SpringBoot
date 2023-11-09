package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.dto.shoppingdto.ProductDTO;
import com.eipulse.teamproject.entity.shoppingentity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {



    @Query("select new com.eipulse.teamproject.dto.shoppingdto.ProductDTO(p) from Product p order by p.subType.subName asc")
    Page<ProductDTO>findByAllProductDto(Pageable pageable);
}