package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.PermissionMoveDTO;
import com.ispan.spirngboot3demo.model.TitleMoveDTO;
import com.ispan.spirngboot3demo.service.TitleMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TitleMoveController {

    private TitleMoveService moveService;
    @Autowired
    public TitleMoveController(TitleMoveService moveService) {
        this.moveService = moveService;
    }

    // add
    @PostMapping("/TitleMove/add")
    public ResponseEntity<?> add(@RequestBody TitleMoveDTO dto){
        try {
            moveService.add(dto);
            return  new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // find once
    @GetMapping("/TitleMove/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        TitleMoveDTO dto = moveService.findById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    // find all
    @GetMapping("/TitleMove/findAll")
    public ResponseEntity<?> findAll(){
        try {
            return new ResponseEntity<>(moveService.findAll(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // delete
    @DeleteMapping("/TitleMove/delete{id}")
    public String delete(@PathVariable("id")Integer id){
        moveService.delete(id);
        return "delete OK";
    }

    // update
    @PutMapping("/TitleMove/update")
    public ResponseEntity<PermissionMoveDTO> update(@RequestBody TitleMoveDTO moveDTO) {

        TitleMoveDTO update = moveService.update(moveDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
