package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entity.Employee;
import com.eipulse.teamproject.entity.Login;
import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import com.eipulse.teamproject.repository.EmpRepository;
import com.eipulse.teamproject.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Transactional
@Service
public class EmpService {
    private EmpRepository empRepository;
    private LoginRepository loginRepository;
    private PasswordEncoder passwordEncoderl;
    @Autowired
    public EmpService(EmpRepository empRepository, LoginRepository loginRepository, PasswordEncoder passwordEncoderl) {
        this.empRepository = empRepository;
        this.loginRepository = loginRepository;
        this.passwordEncoderl = passwordEncoderl;
    }

    public Employee saveEmp(Employee employee) {
        if (employee != null) {
            LocalDate birth = employee.getBirth();
            String password = String.format("%02d", birth.getMonthValue()) + String.format("%02d", birth.getDayOfMonth());
            Employee newEmp = empRepository.save(employee);

            Login login = new Login();
            login.setEmpId(newEmp.getEmpId());
            login.setPassword(passwordEncoderl.encode(password));
            loginRepository.save(login);
            return employee;
        }
        return null;
    }

    public Employee findEmpById(Integer empId) {
        Employee employee = empRepository.findById(empId).orElseThrow(()-> new RuntimeException("查無員工"));

        return employee;
    }

    public List<Employee> findEmpAll() {

        return empRepository.findAll();
    }

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

    public boolean deleteEmp(Integer empId) {
        empRepository.deleteById(empId);
        return true;
    }




}
