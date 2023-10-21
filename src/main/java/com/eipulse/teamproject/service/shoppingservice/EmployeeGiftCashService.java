package com.eipulse.teamproject.service.shoppingservice;


import com.eipulse.teamproject.repository.shoppingrepository.EmployeeGiftCashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeGiftCashService {

    private EmployeeGiftCashRepository employeeGiftCashRepository;

    @Autowired
    public EmployeeGiftCashService(EmployeeGiftCashRepository employeeGiftCashRepository) {
        this.employeeGiftCashRepository = employeeGiftCashRepository;
    }
}
