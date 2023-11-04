package com.eipulse.teamproject.controller.formapprovalcontroller;
import com.eipulse.teamproject.entity.formapproval.Chats;
import com.eipulse.teamproject.repository.formapprovalrepository.ChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
//        (origins = "http://localhost:5173")
//        (origins = "https://maximum-llama-rightly.ngrok-free.app/")
public class ChatsController {

    private ChatsRepository chatsRepo;
    private SimpMessagingTemplate messagingTemplate;
    @Value("${upload.path}")
    private String uploadPath;


    @Autowired
    public ChatsController(ChatsRepository chatsRepo, SimpMessagingTemplate messagingTemplate) {
        this.chatsRepo = chatsRepo;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/Chats")
    public List<Chats> getRoom(@RequestParam int roomId){
        return chatsRepo.findRoom(roomId);
    }

    @PostMapping ("/Chats")
    public void sendMessage(@RequestPart(value = "file", required = false) MultipartFile file,
                            @RequestPart(value = "data") Chats chat) throws IOException {

        if(file!=null){
            String directoryPath = uploadPath + "/chats/" + chat.getRoomId() + "/" + chat.getEmpId();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 這裡會建立整個路徑，包括中間可能不存在的目錄
            }
            String filePath = directoryPath + "/" + file.getOriginalFilename();
            file.transferTo(new File(filePath));
        }
        chatsRepo.save(chat);
        messagingTemplate.convertAndSend("/topic/"+chat.getRoomId()+"/chats",chat);
    }
}
