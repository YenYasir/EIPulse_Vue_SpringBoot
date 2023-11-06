package com.eipulse.teamproject.controller.employeecontroller;

import com.eipulse.teamproject.entity.employee.Permission;
import com.eipulse.teamproject.service.employeeservice.PermissionService;
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
    public ResponseEntity<?> addPermission(@RequestBody Permission permission) {
        permissionService.addPermission(permission);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查詢單筆
    @GetMapping("/permission/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(permissionService.findById(id), HttpStatus.OK);
    }

    // 查詢全部權限
    @GetMapping("/permission/findAll")
    public List<Permission> findAll() {
        return permissionService.findAllPermission();
    }

    // 更新權限
    @Transactional
    @PutMapping("/permission/update")
    public ResponseEntity<?> update(@RequestBody Permission permission) {
        return new ResponseEntity<>(permissionService.updatePermission(permission.getPermissionId(), permission.getPermissionName(), permission.getPermissionStatement()), HttpStatus.OK);
    }

    // 刪除權限
    @DeleteMapping("/permission/delete{id}")
    public ResponseEntity<?> deletePermission(@PathVariable("id") Integer id) {
        permissionService.deletePermission(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
