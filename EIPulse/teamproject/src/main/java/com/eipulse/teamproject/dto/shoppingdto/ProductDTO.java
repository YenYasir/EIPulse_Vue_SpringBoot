package com.eipulse.teamproject.dto.shoppingdto;


import com.eipulse.teamproject.entity.shoppingentity.Product;
import com.eipulse.teamproject.entity.shoppingentity.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {
    private String productTypeName;
    private String subTypeName;
    private Integer id;
    private String productName;
    private Integer subTypeId;
    private String description;
    private Integer price;
    private Integer stockQuantity;
    private String imageUrl;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public ProductDTO() {
    }

    public ProductDTO(String productName, Integer subTypeId, String description, Integer price, Integer stockQuantity, String imageUrl) {
        this.productName = productName;
        this.subTypeId = subTypeId;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Integer id, String productName, Integer subTypeId, String description, Integer price, Integer stockQuantity, String imageUrl) {
        this.id = id;
        this.productName = productName;
        this.subTypeId = subTypeId;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Integer id, String productName, String description, Integer price, Integer stockQuantity, String imageUrl) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(String productName, Integer subTypeId, String description, Integer price, Integer stockQuantity, String imageUrl, String status) {
        this.productName = productName;
        this.subTypeId = subTypeId;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public ProductDTO(Integer id, String productName, Integer subTypeId, String description, Integer price, Integer stockQuantity, String imageUrl, String status) {
        this.id = id;
        this.productName = productName;
        this.subTypeId = subTypeId;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public ProductDTO(Product product) {
        this.productTypeName = product.getSubType().getProductType().getTypeName();
        this.subTypeName = product.getSubType().getSubName();
        this.id = product.getId();
        this.productName = product.getProductName();
        this.subTypeId = product.getSubType().getId();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.imageUrl = product.getImageUrl();
        this.status = product.getStatus();
        this.updatedAt = product.getUpdatedAt();
    }

    public ProductDTO(Integer id) {
        this.id = id;
    }

}
