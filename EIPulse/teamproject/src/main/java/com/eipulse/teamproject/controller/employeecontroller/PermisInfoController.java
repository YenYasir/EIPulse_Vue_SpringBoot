package com.eipulse.teamproject.controller.employeecontroller;

import com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO;
import com.eipulse.teamproject.entity.employee.PermissionInfo;
import com.eipulse.teamproject.service.employeeservice.PermisInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PermisInfoController {


    private PermisInfoService infoService;

    @Autowired
    public PermisInfoController(PermisInfoService infoService) {
        this.infoService = infoService;
    }

    // add info
    @PostMapping("/permissionInfo/add")
    public ResponseEntity<?> add(@RequestBody PermissionInfoDTO infoDTO) {
        infoService.addInfo(infoDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查詢單筆
    @GetMapping("/permissionInfo/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(infoService.findById(id), HttpStatus.OK);
    }

    // 查詢全部
    @GetMapping("/permissionInfo/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(infoService.findAll(), HttpStatus.OK);
    }


    // 刪除職位
    @DeleteMapping("/permissionInfo/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        infoService.deleteInfo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Update permission info
    @Transactional
    @PutMapping("/permissionInfo/update")
    public ResponseEntity<?> update(@RequestBody PermissionInfoDTO permissionInfoDTO) {
        return new ResponseEntity<>(infoService.updateAndLogChange(permissionInfoDTO), HttpStatus.OK);
    }
}
