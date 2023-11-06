package com.eipulse.teamproject.service.formapprovalservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    public void saveFile(List<MultipartFile> files,Integer empId) throws IOException {
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String directoryPath = uploadPath + "/" + empId;
                File directory = new File(directoryPath);

                if (!directory.exists()) {
                    directory.mkdirs(); // 這裡會建立整個路徑，包括中間可能不存在的目錄
                }

                String filePath = directoryPath + "/" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
            }
        }
    }
}
