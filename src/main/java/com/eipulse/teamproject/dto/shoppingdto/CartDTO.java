package com.eipulse.teamproject.dto.shoppingdto;

import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CartDTO {
    private Integer empId;
    private Set<CartItem> cartItems;

    public CartDTO(Integer empId, Set<CartItem> cartItems) {
        this.empId = empId;
        this.cartItems = cartItems;
    }
}
