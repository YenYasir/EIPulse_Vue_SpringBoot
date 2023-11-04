package com.eipulse.teamproject.dto.shoppingdto;

import com.eipulse.teamproject.entity.shoppingentity.Cart;
import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CartDTO {
    private Integer empId;
    private Set<CartItem> cartItems;

    public CartDTO(Cart cart) {
        this.empId = cart.getEmployee().getEmpId();
        this.cartItems = cart.getCartItems();
    }

    public CartDTO(Integer empId) {
        this.empId = empId;
    }

    public CartDTO(Integer empId, Set<CartItem> cartItems) {
        this.empId = empId;
        this.cartItems = cartItems;
    }

    public CartDTO() {
    }


}
