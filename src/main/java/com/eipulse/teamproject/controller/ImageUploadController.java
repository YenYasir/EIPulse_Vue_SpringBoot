package com.eipulse.teamproject.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageUploadController {

    private final BlobServiceClient blobServiceClient;



    public ImageUploadController() {
        // 使用你的連接字串初始化 BlobServiceClient
        String connectStr = "DefaultEndpointsProtocol=https;AccountName=eipulseimages;AccountKey=J3OLfPQvTNjhsavqjIZpktnTy8hCx12b3u6t+IdLYwIJ7i/nzSGJuLgF7Do5/SPoBi5+PkLamBDF+AStcEcIBQ==;EndpointSuffix=core.windows.net";
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
    }

    @PostMapping("/image/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file) {
        // 獲取 Blob 容器的引用
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("images");
        // 獲取 Blob 的引用
        BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());
        try {
            // 上傳文件到 Blob
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while uploading the file.";
        }

        // 返回 Blob 的 URL
        System.out.println(blobClient.getBlobUrl());
        return blobClient.getBlobUrl();
    }
}
