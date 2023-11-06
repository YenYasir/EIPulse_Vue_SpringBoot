package com.eipulse.teamproject.controller.shoppingcontroller;

import com.eipulse.teamproject.entity.shoppingentity.ProductType;
import com.eipulse.teamproject.service.shoppingservice.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductTypeController {
    private ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @PostMapping("/productType")
    public ResponseEntity<?> postProductType(@RequestBody ProductType productType) {
        try {
            productTypeService.saveProductType(productType);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/productTypes")
    public ResponseEntity<?> postProducts(@RequestBody List<ProductType> productTypes) {
        try {
            productTypeService.saveListProductType(productTypes);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/productType/{id}")
    public ResponseEntity<?> getProductType(@PathVariable Integer id) {
        ProductType productType = productTypeService.findByIdProductType(id);
        if (productType != null) {
            return new ResponseEntity<>(productType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/productTypes")
    public ResponseEntity<?> getProductTypes(){
            return new ResponseEntity<>(productTypeService.findAllProductType(), HttpStatus.OK);
    }
    @PutMapping("/productType")
    public ResponseEntity<?>putProductType(@RequestBody ProductType productType){

        if(productTypeService.updateProductType(productType)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("/productType/{id}")
    public ResponseEntity<?>deleteProductType(@PathVariable Integer id){
        if(productTypeService.deleteProductType(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
