package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.entity.Employee;
import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import com.eipulse.teamproject.entity.shoppingentity.ShoppingCart;
import com.eipulse.teamproject.repository.EmpRepository;
import com.eipulse.teamproject.repository.shoppingrepository.ShoppingCartRepository;
import com.eipulse.teamproject.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private EmpService empService;
    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,EmpService empService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.empService= empService;
    }

    public ShoppingCart saveShoppingCart(Integer empId){
        Employee employee = empService.findEmpById(empId);

        return shoppingCartRepository.save(new ShoppingCart(employee));
    }

    @Transactional
    public ShoppingCart findOrCreateShoppingCartByEmpId(Integer empId){
        ShoppingCart shoppingCart = shoppingCartRepository.findByEmployee_EmpId(empId);
        if (shoppingCart != null) {
            return shoppingCart;
        }
        return saveShoppingCart(empId);
    }

    public List<ShoppingCart>findAllCart(){
        return shoppingCartRepository.findAll();
    }

    public boolean updateCart(Integer empId, Set<CartItem> cartItemSet){
        return true;
    }
}
