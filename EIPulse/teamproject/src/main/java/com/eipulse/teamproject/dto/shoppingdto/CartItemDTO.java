package com.eipulse.teamproject.dto.shoppingdto;


import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Integer empId;
    private Integer cartItemId;
    private Integer productId;
    private Integer quantity;

    public CartItemDTO() {
    }

    public CartItemDTO( Integer productId, Integer empId, Integer quantity) {

        this.productId = productId;
        this.empId = empId;
        this.quantity = quantity;
    }

    public CartItemDTO( Integer cartItemId, Integer productId, Integer empId, Integer quantity) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.empId = empId;
        this.quantity = quantity;
    }
}
