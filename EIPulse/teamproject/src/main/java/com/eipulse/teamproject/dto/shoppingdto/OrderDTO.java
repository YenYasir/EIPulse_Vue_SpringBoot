package com.eipulse.teamproject.dto.shoppingdto;


import com.eipulse.teamproject.entity.shoppingentity.Order;
import com.eipulse.teamproject.entity.shoppingentity.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class OrderDTO {
    private Integer orderId;
    private Integer empId;
    private Integer totalPrice;
    private String status;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Set<OrderItem> orderItems;


    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.empId = order.getEmployee().getEmpId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.createdAt =order.getCreatedAt();
        this.orderItems = order.getOrderItems();
    }
}
