package com.eipulse.teamproject.controller.shoppingcontroller;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.eipulse.teamproject.config.SecurityConfig;
import com.eipulse.teamproject.dto.shoppingdto.ProductDTO;
import com.eipulse.teamproject.service.shoppingservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// 10/6 CRUD OK
@RestController
public class ProductController {

    private ProductService productService;
    private SecurityConfig securityConfig;
    @Autowired
    public ProductController(ProductService productService,SecurityConfig securityConfig) {
        this.productService = productService;
        this.securityConfig =securityConfig;
    }

    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.saveProduct(productDTO), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> saveProducts(@RequestBody List<ProductDTO> productDTOList){
        if(productDTOList.size()>0){
            productService.saveListProduct(productDTOList);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.findByIdProduct(id), HttpStatus.OK);
    }

    //查詢全部，用於前台
    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return new ResponseEntity<>(productService.findAllProduct(), HttpStatus.OK);
    }
    //查詢全部，用於後台
    @GetMapping("/products/{pageNumber}")
    public ResponseEntity<?> getAllProduct(@PathVariable Integer pageNumber) {
        return new ResponseEntity<>(productService.findAllAsc(pageNumber), HttpStatus.OK);
    }
    @PutMapping("/product")
    public ResponseEntity<?> putProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productDTO), HttpStatus.OK);
    }

    @PutMapping("/product/status")
    public ResponseEntity<?> putStatus(@RequestBody ProductDTO productDTO) {
        productService.changeStatus(productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);
    }
}
