package com.eipulse.teamproject.controller.employeecontroller;

import com.eipulse.teamproject.dto.employeedto.TitleDTO;
import com.eipulse.teamproject.service.employeeservice.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
public class TitleController {

    private TitleService titleService;

    @Autowired
    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    // 新增職位
    @PostMapping("/title/add")
    public ResponseEntity<?> addTitle(@RequestBody TitleDTO titleDTO) {
        titleService.addTitle(titleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查詢單筆
    @GetMapping("/title/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(titleService.findById(id), HttpStatus.OK);
    }

    // 查詢全部職位
    @GetMapping("/title/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(titleService.findAllTitle(), HttpStatus.OK);
    }

    // 更新職位
    @Transactional
    @PutMapping("/title/updateTitle")
    public ResponseEntity<?> update(@RequestBody TitleDTO titleDTO) {
        return new ResponseEntity<>(titleService.update(titleDTO), HttpStatus.OK);
    }


    // 刪除職位
    @DeleteMapping("/title/deleteTitle{id}")
    public ResponseEntity<?> deleteTitle(@PathVariable("id") Integer titleId) {
        titleService.deleteTitle(titleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
