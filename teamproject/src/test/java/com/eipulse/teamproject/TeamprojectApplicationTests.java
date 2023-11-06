package com.eipulse.teamproject;

import com.eipulse.teamproject.dto.shoppingdto.CartDTO;
import com.eipulse.teamproject.service.shoppingservice.CartService;
import com.eipulse.teamproject.service.shoppingservice.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TeamprojectApplicationTests {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	@Test
	@Transactional
	void contextLoads() {
		CartDTO cartDTO = cartService.findOrCreateShoppingCartByEmpId(1001);
		orderService.saveOrder(cartDTO);
	}

}
