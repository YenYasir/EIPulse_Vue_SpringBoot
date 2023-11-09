package com.eipulse.teamproject.controller.shoppingcontroller;

import com.eipulse.teamproject.dto.shoppingdto.CartItemDTO;
import com.eipulse.teamproject.service.shoppingservice.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartItemController {

    private CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/cartItem")
    public ResponseEntity<?> saveCartItem(@RequestBody CartItemDTO cartItemDTO) {
        return new ResponseEntity<>(cartItemService.saveCartItem(cartItemDTO), HttpStatus.OK);
    }

    @PutMapping("/cartItem")
    public ResponseEntity<?> putCartItem(@RequestBody CartItemDTO cartItemDTO) {
        cartItemService.updateCartItem(cartItemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer cartItemId) {
        cartItemService.removeCartItem(cartItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
