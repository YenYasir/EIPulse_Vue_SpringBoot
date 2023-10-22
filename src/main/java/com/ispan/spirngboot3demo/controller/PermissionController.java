package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.Permission;
import com.ispan.spirngboot3demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PermissionController {

   private PermissionService permissionService;
    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    // 新增權限
    @PostMapping("/permission/add")
    public ResponseEntity<?> addPermission(@RequestBody Permission permission){
     try {
         permissionService.addPermission(permission);

         return new ResponseEntity<>(HttpStatus.OK);

     }catch (Exception e){
         return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
     }
    }

    // 查詢單筆
    @GetMapping("/permission/{id}")
    public ResponseEntity<?>findById(@PathVariable Integer id){
        return new ResponseEntity<>(permissionService.findById(id),HttpStatus.OK);
    }

    // 查詢全部權限
    @GetMapping("/permission/findAll")
    public List<Permission> findAll(){
        return permissionService.findAllPermission();
    }

    // 更新權限
    @Transactional
    @PutMapping("/permission/update")
    public ResponseEntity<?> update(@RequestBody Permission permission){
        try {
            return new ResponseEntity<>(permissionService.updatePermission(permission.getPermissionId(), permission.getPermissionName(), permission.getPermissionStatement()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 刪除權限
    @DeleteMapping("/permission/delete{id}")
    public String deletePermission(@PathVariable("id")Integer id) {
        permissionService.deletePermission(id);
        return "delete OK";
    }

}
