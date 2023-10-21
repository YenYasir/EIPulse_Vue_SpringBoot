package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.dto.shoppingdto.ProductDTO;
import com.eipulse.teamproject.entity.shoppingentity.Product;
import com.eipulse.teamproject.entity.shoppingentity.SubType;
import com.eipulse.teamproject.repository.shoppingrepository.ProductRepository;
import com.eipulse.teamproject.repository.shoppingrepository.SubTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private SubTypeRepository subTypeRepository;
    @Autowired
    public ProductService(ProductRepository productRepository,SubTypeRepository subTypeRepository) {
        this.productRepository = productRepository;
        this.subTypeRepository = subTypeRepository;
    }

    public Product saveProduct(ProductDTO productDTO){
        SubType subType = subTypeRepository.findById(productDTO.getSubTypeId()).orElseThrow(null);
        return productRepository.save(new Product(productDTO.getProductName(),subType, productDTO.getDescription(),productDTO.getPrice(), productDTO.getStockQuantity(), productDTO.getImageUrl()));
    }

    public List<Product>saveListProduct(List<ProductDTO>productDTOList){
        List<Product> products  =new ArrayList<>();
        for(ProductDTO productDTO :productDTOList){
            SubType subType = subTypeRepository.findById(productDTO.getSubTypeId()).orElseThrow(()->new RuntimeException("無資料"));
            Product product =new Product(productDTO.getProductName(),subType, productDTO.getDescription(),productDTO.getPrice(), productDTO.getStockQuantity(), productDTO.getImageUrl());
            products.add(product);
        }
        return productRepository.saveAll(products);
    }
    public Product findByIdProduct(Integer id){
        Optional<Product>optional = productRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }else {
            throw new UnsupportedOperationException("無資料");
        }
    }

    public List<Product>findAllProduct(){
        return productRepository.findAll();
    }

    public boolean updateProduct(ProductDTO productDTO){
        Product product =productRepository.findById(productDTO.getId()).orElseThrow(()->new RuntimeException("查無原資料"));
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setStockQuantity(productDTO.getStockQuantity());
        productRepository.save(product);
        return true;
    }

    public boolean deleteProductById(Integer id){
        Optional<Product>optional = productRepository.findById(id);
        if(optional.isPresent()){
            productRepository.deleteById(id);
            return true;
        }else {
            throw new UnsupportedOperationException("無資料");
        }
    }
}
