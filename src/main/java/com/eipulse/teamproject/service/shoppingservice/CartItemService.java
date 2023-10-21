package com.eipulse.teamproject.service.shoppingservice;


import com.eipulse.teamproject.dto.shoppingdto.ItemDTO;
import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import com.eipulse.teamproject.repository.shoppingrepository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem saveCartItem(ItemDTO itemDTO){
        return null;
    }
}
