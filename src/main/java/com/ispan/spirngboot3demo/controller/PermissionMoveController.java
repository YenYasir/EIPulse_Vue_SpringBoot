package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.PermissionMove;
import com.ispan.spirngboot3demo.model.PermissionMoveDTO;
import com.ispan.spirngboot3demo.service.PermisMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
        moveService.addPermisMove(dto);
        return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // find once
    @GetMapping("/permissionMove/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        PermissionMoveDTO dto = moveService.findById(id);
        System.out.println(dto);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    // find all
    @GetMapping("/permissionMove/findAll")
    public ResponseEntity<?> findAll(){
        try {
            return  new ResponseEntity<>(moveService.findAll(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    // delete
    @DeleteMapping("/permissionMove/delete{id}")
    public String delete(@PathVariable("id")Integer id){
        moveService.delete(id);
        return "delete OK";
    }

    // update
    @PutMapping("/permissionMove/update")
    public ResponseEntity<PermissionMoveDTO> update(@RequestBody PermissionMoveDTO moveDTO) {
        PermissionMoveDTO updatedMoveDTO = moveService.update(moveDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
