package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.dto.shoppingdto.CartDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.shoppingentity.Cart;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.shoppingrepository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CartService {

    private CartRepository cartRepository;
    private EmployeeRepository employeeRepository;
    @Autowired
    public CartService(CartRepository cartRepository, EmployeeRepository employeeRepository) {
        this.cartRepository = cartRepository;
        this.employeeRepository= employeeRepository;
    }

//    新增購物車
    public Cart saveShoppingCart(Integer empId){
        Employee employee = employeeRepository.findById(empId).orElseThrow(()->new RuntimeException("error"));

        return cartRepository.save(new Cart(employee));
    }
    //新增購物車，當沒有時自動新增
    @Transactional
    public CartDTO findOrCreateShoppingCartByEmpId(Integer empId){
        Cart cart = cartRepository.findByEmployee_EmpId(empId);
        if (cart != null) {
            return new CartDTO(cart);
        }
        return new CartDTO(saveShoppingCart(empId));
    }


}
