package com.eipulse.teamproject.dto.shoppingdto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private Integer cartId;
    private Integer productId;

    private Integer quantity;
}
