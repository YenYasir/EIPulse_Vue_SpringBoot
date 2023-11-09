package com.eipulse.teamproject.controller.employeecontroller;

import com.eipulse.teamproject.dto.employeedto.PermissionMoveDTO;
import com.eipulse.teamproject.service.employeeservice.PermisMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PermissionMoveController {
    private PermisMoveService moveService;

    @Autowired
    public PermissionMoveController(PermisMoveService moveService) {
        this.moveService = moveService;
    }

    // 增加
    @PostMapping("/permissionMove/add")
    public ResponseEntity<?> addPermissionMove(@RequestBody PermissionMoveDTO dto) {
        moveService.addPermisMove(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // find once
    @GetMapping("/permissionMove/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(moveService.findById(id), HttpStatus.OK);
    }

    // find all
    @GetMapping("/permissionMove/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(moveService.findAll(), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/permissionMove/delete{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        moveService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // update
    @PutMapping("/permissionMove/update")
    public ResponseEntity<PermissionMoveDTO> update(@RequestBody PermissionMoveDTO moveDTO) {
        return new ResponseEntity<>(moveService.update(moveDTO), HttpStatus.OK);
    }
    // 普通分頁
    @GetMapping("/permissionMove/paged/{pageNumber}")
    @ResponseStatus(HttpStatus.OK)  // 這裡設置返回的 HTTP 狀態碼為 200
    public Page<PermissionMoveDTO> getEmployeesByPage(@PathVariable Integer pageNumber) {
        return moveService.findByPage(pageNumber);
    }

}
