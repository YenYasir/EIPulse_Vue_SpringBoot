package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Employee;
import com.eipulse.teamproject.entitys.Login;
import com.eipulse.teamproject.repository.EmpRepository;
import com.eipulse.teamproject.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class EmpServiceImp implements EmpService {
    private EmpRepository empRepository;
    private LoginRepository loginRepository;
    private PasswordEncoder passwordEncoderl;
    @Autowired
    public EmpServiceImp(EmpRepository empRepository, LoginRepository loginRepository, PasswordEncoder passwordEncoderl) {
        this.empRepository = empRepository;
        this.loginRepository = loginRepository;
        this.passwordEncoderl = passwordEncoderl;
    }

    @Override
    public Employee saveEmp(Employee employee) {
        if (employee != null) {
            LocalDate birth = employee.getBirth();
            String password = String.format("%02d", birth.getMonthValue()) + String.format("%02d", birth.getDayOfMonth());
            Employee newEmp = empRepository.save(employee);

            Login login = new Login();
            login.setEmpId(newEmp.getEmpId());
            login.setPassWord(passwordEncoderl.encode(password));
            loginRepository.save(login);
            return employee;
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
    public boolean updateEmp(Integer empId, Employee newEmployee) {
        Optional<Employee> optional = empRepository.findById(empId);

        if (optional != null) {
            Employee oldEmployee = optional.get();
            if (oldEmployee != newEmployee) {
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
