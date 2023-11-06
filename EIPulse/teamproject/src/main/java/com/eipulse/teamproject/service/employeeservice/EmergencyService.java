package com.eipulse.teamproject.service.employeeservice;


import com.eipulse.teamproject.dto.employeedto.EmergencyDTO;
import com.eipulse.teamproject.entity.employee.Emergency;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.repository.employeerepository.EmergencyRepository;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmergencyService {

    private EmergencyRepository emergencyRepository;
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmergencyService(EmergencyRepository emergencyRepository, EmployeeRepository employeeRepository) {
        this.emergencyRepository = emergencyRepository;
        this.employeeRepository = employeeRepository;
    }



    // add
    public void addEmergency(EmergencyDTO emergencyDTO) {
        Employee emp = employeeRepository.findById(emergencyDTO.getEmpId())
                .orElseThrow(() -> new NoSuchElementException("Employee with ID " + emergencyDTO.getEmpId() + " not found."));

        emergencyRepository.save(new Emergency(emergencyDTO.getEmergencyName(), emergencyDTO.getPhone(), emergencyDTO.getRelation(), emp));
    }

    // find once
    public Emergency findById(Integer id){
        Optional<Emergency> optional = emergencyRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    // delete
    public void deleteEmergency(Integer id){emergencyRepository.deleteById(id);}

    // find all
    public List<Emergency> findAllEmergency(){
        return emergencyRepository.findAll();
    }

    // update(緊急聯絡人姓名、電話、關係)
    public Emergency updateEmergency(EmergencyDTO dto) {
        Emergency oldData = emergencyRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Emergency with ID  not found."));

        oldData.setEmergencyName(dto.getEmergencyName());
        oldData.setPhone(dto.getPhone());
        oldData.setRelation(dto.getRelation());

        return emergencyRepository.save(oldData);
    }
}
