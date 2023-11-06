package com.eipulse.teamproject.service.shoppingservice;


import com.eipulse.teamproject.repository.shoppingrepository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private OrderItemRepository orderItemRepository;
    private CartService cartService;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, CartService cartService) {
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
    }
}
