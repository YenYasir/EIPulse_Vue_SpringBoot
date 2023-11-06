package com.eipulse.teamproject.controller.shoppingcontroller;

import com.eipulse.teamproject.dto.shoppingdto.SubTypeDTO;
import com.eipulse.teamproject.service.shoppingservice.SubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// 10/6 CRUD OK

@RestController
public class SubTypeController {
    private SubTypeService subTypeService;

    @Autowired
    public SubTypeController(SubTypeService subTypeService) {
        this.subTypeService = subTypeService;
    }

    @PostMapping("/subType")
    public ResponseEntity<?> postSubType(@RequestBody SubTypeDTO subTypeDTO) {
        subTypeService.saveSubType(subTypeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subType/{id}")
    public ResponseEntity<?> getSubType(@PathVariable Integer id) {
        return new ResponseEntity<>(subTypeService.findByIdSubType(id), HttpStatus.OK);
    }

    @GetMapping("/subTypes/{id}")
    public ResponseEntity<?> getSubTypes(@PathVariable Integer id) {
        return new ResponseEntity<>(subTypeService.findByProductId(id), HttpStatus.OK);
    }

    @DeleteMapping("/subType/{id}")
    public ResponseEntity<?> deleteSubType(@PathVariable Integer id) {
        return new ResponseEntity<>(subTypeService.deleteSubType(id), HttpStatus.OK);

    }
}
