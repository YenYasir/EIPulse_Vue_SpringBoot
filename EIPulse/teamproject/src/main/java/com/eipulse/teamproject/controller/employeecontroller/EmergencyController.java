package com.eipulse.teamproject.controller.employeecontroller;

import com.eipulse.teamproject.dto.employeedto.DeptDTO;
import com.eipulse.teamproject.dto.employeedto.EmergencyDTO;
import com.eipulse.teamproject.entity.employee.Emergency;
import com.eipulse.teamproject.service.employeeservice.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmergencyController {

    private EmergencyService emergencyService;

    @Autowired
    public EmergencyController(EmergencyService emergencyService) {
        this.emergencyService = emergencyService;
    }

    // 新增緊急聯絡人
    @PostMapping("/emergency/add")
    public ResponseEntity<?> addEmergency(@RequestBody EmergencyDTO emergencyDTO) {
        emergencyService.addEmergency(emergencyDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查詢單筆
    @GetMapping("/emergency/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(emergencyService.findById(id), HttpStatus.OK);
    }

    // 查詢全緊急聯絡人
    @GetMapping("/emergency/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(emergencyService.findAllEmergency(), HttpStatus.OK);
    }


    // 更新緊急聯絡人
    @Transactional
    @PutMapping("/emergency/update")
    public ResponseEntity<?> update(@RequestBody EmergencyDTO dto){
        try {
            return new ResponseEntity<>(emergencyService.updateEmergency(dto),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 刪除緊急聯絡人
    @DeleteMapping("/emergency/delete/{id}")
    public ResponseEntity<?> deleteEmergencyt(@PathVariable("id") Integer id) {
        emergencyService.deleteEmergency(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 普通分頁
    @GetMapping("/emergency/paged/{pageNumber}")
    @ResponseStatus(HttpStatus.OK)  // 這裡設置返回的 HTTP 狀態碼為 200
    public Page<EmergencyDTO> getEmergencyByPage(@PathVariable Integer pageNumber) {
        return emergencyService.findByPage(pageNumber);
    }
}
