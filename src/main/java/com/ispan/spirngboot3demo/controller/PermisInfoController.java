package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.PermissionInfo;
import com.ispan.spirngboot3demo.model.PermissionInfoDTO;
import com.ispan.spirngboot3demo.model.Title;
import com.ispan.spirngboot3demo.service.PermisInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> add(@RequestBody PermissionInfoDTO infoDTO){
        try {
            infoService.addInfo(infoDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // 查詢單筆
    @GetMapping("/permissionInfo/{id}")
    public ResponseEntity<?>findById(@PathVariable Integer id){
        PermissionInfoDTO permissionInfo = infoService.findById(id);
        System.out.println(permissionInfo);
        return new ResponseEntity<>(permissionInfo,HttpStatus.OK);
    }

    // 查詢全部
    @GetMapping("/permissionInfo/findAll")
    public ResponseEntity<?> findAll(){
        List<PermissionInfoDTO> permissionInfoDTOs = infoService.findAll();
        return new ResponseEntity<>(permissionInfoDTOs,HttpStatus.OK);
    }


    // 刪除職位
    @DeleteMapping("/permissionInfo/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        infoService.deleteInfo(id);
        return "delete OK  超派";
    }


    // Update permission info
    @PutMapping("/permissionInfo/update")
    public ResponseEntity<?> update(@RequestBody PermissionInfoDTO permissionInfoDTO) {
        try {
            PermissionInfo info = infoService.update(permissionInfoDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
