package com.eipulse.teamproject.controller.employeecontroller;
import com.eipulse.teamproject.dto.employeedto.TitleMoveDTO;
import com.eipulse.teamproject.service.employeeservice.TitleMoveService;
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
    public ResponseEntity<?> add(@RequestBody TitleMoveDTO dto) {
        moveService.add(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // find once
    @GetMapping("/TitleMove/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        return new ResponseEntity<>(moveService.findById(id), HttpStatus.OK);
    }

    // find all
    @GetMapping("/TitleMove/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(moveService.findAll(), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/TitleMove/delete{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        moveService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // update
    @PutMapping("/TitleMove/update")
    public ResponseEntity<?> update(@RequestBody TitleMoveDTO moveDTO) {
        return new ResponseEntity<>(moveService.update(moveDTO),HttpStatus.OK);
    }
}