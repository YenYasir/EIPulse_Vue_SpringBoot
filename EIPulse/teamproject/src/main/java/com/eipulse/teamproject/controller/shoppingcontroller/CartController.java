package com.eipulse.teamproject.controller.shoppingcontroller;


import com.eipulse.teamproject.dto.shoppingdto.CartDTO;
import com.eipulse.teamproject.entity.shoppingentity.Cart;
import com.eipulse.teamproject.service.shoppingservice.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/{empId}")
    public ResponseEntity<?> getCart(@PathVariable Integer empId) {
        return new ResponseEntity<>(cartService.findOrCreateShoppingCartByEmpId(empId), HttpStatus.OK);
    }
}
