package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.Emergency;
import com.ispan.spirngboot3demo.model.EmergencyDTO;
import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.repository.EmergencyRepository;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
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

    // update
    public Emergency updateEmergency(Integer emergencyId, String emergencyName, String phone, String relation) {
        Emergency oldData = emergencyRepository.findById(emergencyId)
                .orElseThrow(() -> new RuntimeException("Emergency with ID " + emergencyId + " not found."));

        oldData.setEmergencyName(emergencyName);
        oldData.setPhone(phone);
        oldData.setRelation(relation);

        return emergencyRepository.save(oldData);
    }

}
