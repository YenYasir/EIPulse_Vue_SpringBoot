package com.eipulse.teamproject.webSocket.controller;

import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.webSocket.entity.MessageEntity;
import com.eipulse.teamproject.webSocket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    @Autowired
    private MessageService messageService;
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private EmployeeRepository empRepository;

    @PostMapping("/sendMsg")
    public void sendMsg(@RequestPart(value = "file", required = false) MultipartFile file,
                        @RequestPart(value = "data") MessageEntity messageEntity) throws IOException {
        if(file!=null){
            String directoryPath = uploadPath + "/privateChats/" + messageEntity.getUser1() +  messageEntity.getUser2() + "/" + messageEntity.getSender();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 這裡會建立整個路徑，包括中間可能不存在的目錄
            }
            String filePath = directoryPath + "/" + file.getOriginalFilename();
            file.transferTo(new File(filePath));
        }
        messageService.sendToUser(messageEntity);
    }

    @PostMapping("/getMsg")
    public ResponseEntity<?> getMsg(@RequestBody MessageEntity messageEntity,@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new ResponseEntity<>(messageService.getMsg(messageEntity,pageable), HttpStatus.OK);
    }
    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers(@RequestParam String my){
        return new ResponseEntity<>(messageService.getUser(my), HttpStatus.OK);
    }

}
