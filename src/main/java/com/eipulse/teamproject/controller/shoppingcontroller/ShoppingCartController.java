package com.eipulse.teamproject.controller.shoppingcontroller;


import com.eipulse.teamproject.dto.EmployeeDTO;
import com.eipulse.teamproject.dto.shoppingdto.CartDTO;
import com.eipulse.teamproject.entity.shoppingentity.ShoppingCart;
import com.eipulse.teamproject.service.shoppingservice.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShoppingCartController {

    private ShoppingCartService cartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/{empId}")
    public ResponseEntity<?> getCart(@PathVariable Integer empId){
        try{
            ShoppingCart shoppingCart = cartService.findOrCreateShoppingCartByEmpId(empId);
            return new ResponseEntity<>(new CartDTO(shoppingCart.getId(),shoppingCart.getCartItems()),HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }
}
