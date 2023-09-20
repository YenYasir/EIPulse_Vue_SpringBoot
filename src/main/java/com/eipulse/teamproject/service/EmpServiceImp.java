package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Employee;
import com.eipulse.teamproject.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class EmpServiceImp implements EmpService {
    private EmpRepository empRepository;

    @Autowired
    public EmpServiceImp(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    @Override
    public Employee saveEmp(Employee employee) {
        if (employee != null) {
            return empRepository.save(employee);
        }
        return null;
    }

    @Override
    public Employee findEmpById(Integer empId) {
        Optional<Employee> optional = empRepository.findById(empId);
        if (optional != null) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Employee> findEmpAll() {

        return empRepository.findAll();
    }

    @Override
    public boolean updateEmp(Integer empId,Employee newEmployee) {
        Optional<Employee> optional = empRepository.findById(empId);

        if(optional!=null){
            Employee oldEmployee = optional.get();
            if(oldEmployee != newEmployee){
                empRepository.save(newEmployee);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteEmp(Integer empId) {
        empRepository.deleteById(empId);
        return true;
    }
}
