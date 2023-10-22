package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.Title;
import com.ispan.spirngboot3demo.model.TitleDTO;
import com.ispan.spirngboot3demo.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TitleController {

    private TitleService titleService;
    @Autowired
    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    // 新增職位
    @PostMapping("/title/add")
    public ResponseEntity<?> addTitle(@RequestBody TitleDTO titleDTO){
        try {
            titleService.addTitle(titleDTO);
            System.out.println(titleDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // 查詢單筆
    @GetMapping("/title/{id}")
    public ResponseEntity<?>findById(@PathVariable Integer id){
        return new ResponseEntity<>(titleService.findById(id),HttpStatus.OK);
    }
    // 查詢全部職位
    @GetMapping("/title/findAll")
    public List<Title> findAll(){
        List<Title> list = titleService.findAllTitle();
        if (list!=null){
            return list;
        }
        return null;
    }

    // 更新職位
    @Transactional
    @PutMapping("/title/updateTitle")
    public ResponseEntity<?> update(@RequestBody TitleDTO titleDTO){
        try {
            return new ResponseEntity<>(titleService.update(titleDTO),HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    // 刪除職位
    @DeleteMapping("/title/deleteTitle{id}")
    public String deleteTitle(@PathVariable("id")Integer titleId){
        System.out.println("超派");
        titleService.deleteTitle(titleId);
        return "delete OK  超派";
    }









}
