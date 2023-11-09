package com.eipulse.teamproject.controller.employeecontroller;

import com.eipulse.teamproject.dto.employeedto.ResignDTO;
import com.eipulse.teamproject.service.employeeservice.ResignRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResignRecordController {


    private ResignRecordService resignService;

    @Autowired
    public ResignRecordController(ResignRecordService resignService) {
        this.resignService = resignService;
    }

    // 新增離職紀錄
    @PostMapping("/resign/add")
    public ResponseEntity<?> add(@RequestBody ResignDTO resignDTO) {
        resignService.addResign(resignDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 查詢單筆
    @GetMapping("/resign/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(resignService.findById(id), HttpStatus.OK);
    }

    // 查詢全部離職紀錄
    @GetMapping("/resign/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(resignService.findAllResign(), HttpStatus.OK);
    }

    // 更新離職
    @Transactional
    @PutMapping("/resign/update")
    public ResponseEntity<?> update(@RequestBody ResignDTO resignDTO) {
        return new ResponseEntity<>(resignService.update(resignDTO), HttpStatus.OK);
    }


    // 刪除離職紀錄
    @DeleteMapping("/resign/delete{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer resignId) {
        resignService.deleteResign(resignId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
