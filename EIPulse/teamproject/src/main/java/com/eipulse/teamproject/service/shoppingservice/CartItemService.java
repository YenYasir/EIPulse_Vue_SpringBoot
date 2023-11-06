package com.eipulse.teamproject.service.shoppingservice;


import com.eipulse.teamproject.dto.shoppingdto.CartItemDTO;
import com.eipulse.teamproject.entity.shoppingentity.Cart;
import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import com.eipulse.teamproject.entity.shoppingentity.Product;
import com.eipulse.teamproject.repository.shoppingrepository.CartItemRepository;
import com.eipulse.teamproject.repository.shoppingrepository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class CartItemService {

    private CartItemRepository cartItemRepository;
    private ProductService productService;
    private CartService cartService;
    private CartRepository cartRepository;


    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductService productService, CartService cartService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    //新增購物車商品
    public CartItem saveCartItem(CartItemDTO itemDTO) {
        Product product = productService.findByIdProduct(itemDTO.getProductId());
        Cart cart = cartRepository.findByEmployee_EmpId(itemDTO.getEmpId());
        Set<CartItem> cartItemList = cart.getCartItems();
        CartItem existingCartItem = null;
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().getId() == itemDTO.getProductId()) {
                existingCartItem = cartItem;
            }
        }
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + itemDTO.getQuantity());
            return cartItemRepository.save(existingCartItem);
        } else {
            return cartItemRepository.save(new CartItem(cart, product, itemDTO.getQuantity()));
        }
    }

    //更新購物車商品
    public void updateCartItem(CartItemDTO itemDTO) {
        Optional<CartItem> optional = cartItemRepository.findById(itemDTO.getCartItemId());
        if (optional.isPresent()) {
            CartItem cartItem = optional.get();
            cartItem.setQuantity(itemDTO.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            saveCartItem(itemDTO);
        }
    }

    //移除購物車商品
    public boolean removeCartItem(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return true;
    }
}
