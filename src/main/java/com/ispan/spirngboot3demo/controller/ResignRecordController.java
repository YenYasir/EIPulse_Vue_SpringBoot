package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.ResignDTO;
import com.ispan.spirngboot3demo.model.ResignRecord;
import com.ispan.spirngboot3demo.model.Title;
import com.ispan.spirngboot3demo.model.TitleDTO;
import com.ispan.spirngboot3demo.service.ResignRecordService;
import com.ispan.spirngboot3demo.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResignRecordController {


        private ResignRecordService resignService;
        @Autowired
        public ResignRecordController(ResignRecordService resignService) {
            this.resignService = resignService;
        }

    // 新增離職紀錄
        @PostMapping("/resign/add")
        public ResponseEntity<?> add(@RequestBody ResignDTO resignDTO){
            try {
                resignService.addResign(resignDTO);
                System.out.println(resignDTO);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }


        // 查詢單筆
        @GetMapping("/resign/{id}")
        public ResponseEntity<?>findById(@PathVariable Integer id){
            return new ResponseEntity<>(resignService.findById(id),HttpStatus.OK);
        }
        // 查詢全部離職紀錄
        @GetMapping("/resign/findAll")
        public ResponseEntity<?> findAll(){
           List<ResignDTO> dto = resignService.findAllResign();
           return new ResponseEntity<>(dto,HttpStatus.OK);
        }

        // 更新離職
        @Transactional
        @PutMapping("/resign/update")
        public ResponseEntity<?> update(@RequestBody ResignDTO resignDTO){
            try {
                System.out.println(resignDTO);
                return new ResponseEntity<>(resignService.update(resignDTO),HttpStatus.OK);
            }catch (RuntimeException e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }


        // 刪除離職紀錄
        @DeleteMapping("/resign/delete{id}")
        public String delete(@PathVariable("id")Integer resignId){
            System.out.println("超派");
            resignService.deleteResign(resignId);
            return "delete OK  超派";
        }



}
