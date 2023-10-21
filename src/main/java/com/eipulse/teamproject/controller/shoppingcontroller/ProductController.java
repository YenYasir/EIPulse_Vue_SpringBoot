package com.eipulse.teamproject.controller.shoppingcontroller;


import com.eipulse.teamproject.dto.shoppingdto.ProductDTO;
import com.eipulse.teamproject.service.shoppingservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 10/6 CRUD OK
@RestController
public class ProductController {

    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<?>saveProduct(@RequestBody ProductDTO productDTO){
        if(productDTO!=null){
            return new ResponseEntity<>(productService.saveProduct(productDTO), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/products")
    public ResponseEntity<?> saveProducts(@RequestBody List<ProductDTO> productDTOList){
        if(productDTOList.size()>0){
            return new ResponseEntity<>(productService.saveListProduct(productDTOList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id){
        try{
            return new ResponseEntity<>(productService.findByIdProduct(id),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(){
        try{
            return new ResponseEntity<>(productService.findAllProduct(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/product")
    public ResponseEntity<?> putProduct(@RequestBody ProductDTO productDTO){
        try{
            return new ResponseEntity<>(productService.updateProduct(productDTO),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        try{
            return new ResponseEntity<>(productService.deleteProductById(id),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
