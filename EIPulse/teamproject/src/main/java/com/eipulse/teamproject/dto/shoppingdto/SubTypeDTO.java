package com.eipulse.teamproject.dto.shoppingdto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubTypeDTO {
    private Integer productTypeId;
    private String subName;

    public SubTypeDTO() {
    }

    public SubTypeDTO(Integer productTypeId, String subName) {
        this.productTypeId = productTypeId;
        this.subName = subName;
    }
}
