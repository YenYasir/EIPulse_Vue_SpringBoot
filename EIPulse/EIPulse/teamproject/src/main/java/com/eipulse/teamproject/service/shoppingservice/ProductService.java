package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.dto.shoppingdto.ProductDTO;
import com.eipulse.teamproject.entity.shoppingentity.OrderItem;
import com.eipulse.teamproject.entity.shoppingentity.Product;
import com.eipulse.teamproject.entity.shoppingentity.ProductType;
import com.eipulse.teamproject.entity.shoppingentity.SubType;
import com.eipulse.teamproject.repository.shoppingrepository.ProductRepository;
import com.eipulse.teamproject.repository.shoppingrepository.SubTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private SubTypeRepository subTypeRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, SubTypeRepository subTypeRepository) {
        this.productRepository = productRepository;
        this.subTypeRepository = subTypeRepository;
    }

    //新增商品
    public Product saveProduct(ProductDTO productDTO) {
        SubType subType = subTypeRepository.findById(productDTO.getSubTypeId()).orElseThrow(null);
        return productRepository.save(new Product(productDTO.getProductName(), subType, productDTO.getDescription(), productDTO.getPrice(), productDTO.getStockQuantity(), productDTO.getImageUrl()));
    }

    //批量新增
    public void saveListProduct(List<ProductDTO> productDTOList) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList) {
            SubType subType = subTypeRepository.findById(productDTO.getSubTypeId()).orElseThrow(() -> new RuntimeException("無資料"));
            Product product = new Product(productDTO.getProductName(), subType, productDTO.getDescription(), productDTO.getPrice(), productDTO.getStockQuantity(), productDTO.getImageUrl(), productDTO.getStatus());
            products.add(product);
        }
        productRepository.saveAll(products);
    }

    //依照產品 id 查詢
    public Product findByIdProduct(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UnsupportedOperationException("無資料");
        }
    }

    //查詢所有商品
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    //查詢後排序
    public Page<ProductDTO>findAllAsc(Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1, 5);

        return productRepository.findByAllProductDto(pageable);
    }

    //產品更新
    public boolean updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("查無原資料"));
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setStatus(productDTO.getStatus());
        productRepository.save(product);
        return true;
    }

    //更新產品數量，用戶訂單建立扣除銷量
    public boolean updateStockQuantity(ProductDTO productDTO, OrderItem orderItem) {

        Product product = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("查無原資料"));
        int newQuantity = product.getStockQuantity() - orderItem.getQuantity();
        if (newQuantity >=0){
            product.setStockQuantity(newQuantity);
            productRepository.save(product);
            return true;
        }else {
            return false;
        }
    }

    //上下架商品
    public void changeStatus(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("無資料"));
        if ("上架".equals(product.getStatus())) {
            product.setStatus("下架");
        } else {
            product.setStatus("上架");
        }
        productRepository.save(product);
    }

    //商品刪除
    public boolean deleteProductById(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new UnsupportedOperationException("無資料");
        }
    }


}
