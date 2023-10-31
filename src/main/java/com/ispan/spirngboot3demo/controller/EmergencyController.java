package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.Emergency;
import com.ispan.spirngboot3demo.model.EmergencyDTO;
import com.ispan.spirngboot3demo.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> addEmergency(@RequestBody EmergencyDTO emergencyDTO){
     try {
         emergencyService.addEmergency(emergencyDTO);
         return new ResponseEntity<>(HttpStatus.OK);

     }catch (Exception e){
         return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
     }
    }

    // 查詢單筆
    @GetMapping("/emergency/{id}")
    public ResponseEntity<?>findById(@PathVariable Integer id){
        return new ResponseEntity<>(emergencyService.findById(id),HttpStatus.OK);
    }

    // 查詢全緊急聯絡人
    @GetMapping("/emergency/findAll")
    public List<Emergency> findAll(){
        return emergencyService.findAllEmergency();
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
    @DeleteMapping("/emergency/delete{id}")
    public String deleteEmergencyt(@PathVariable("id")Integer id) {
        emergencyService.deleteEmergency(id);
        return "delete OK";
    }

}
