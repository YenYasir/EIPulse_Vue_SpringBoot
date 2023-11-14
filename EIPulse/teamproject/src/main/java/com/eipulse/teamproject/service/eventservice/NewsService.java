package com.eipulse.teamproject.service.eventservice;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.eipulse.teamproject.dto.eventdto.NewsDTO;
import com.eipulse.teamproject.repository.eventrepository.NewsRepository;
import com.eipulse.teamproject.entity.evententity.Event;
import com.eipulse.teamproject.entity.evententity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@Transactional
public class NewsService {
	
	private NewsRepository newsRepo;

    @Autowired
    public NewsService(NewsRepository newsRepo) {
        this.newsRepo = newsRepo;
    }

//    新增消息
    public void saveNews(News news) {
    	newsRepo.save(news);
    }
    
    // 修改消息
    public void updateNews(Integer id, News news) {
    	newsRepo.updateNews(id, news); 
    }
    
//// 依照消息id刪除消息
//    public void deleteNewsById(Integer newsId) {
//        newsRepo.deleteById(newsId);
//    }
    
    // 標題模糊搜尋消息
//    PageRequest.of 方法中的 page 預設值是 0，表示第一頁，size為每頁要返回的筆數
    public Page<NewsDTO> searchByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.fingNewsByTitle(title, pageable);
    }

    // 依照時間順序顯示最新快訊
    public Page<NewsDTO> findAllNewsByPostTime(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.findAllNewsByPostTime(pageable);
    }
    
    // 顯示下架消息
    public Page<NewsDTO> findAllRemovedNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.findRemovedNews(pageable);
    }
    
    // 轉換傳回的 DTO 為 Entity存入資料庫
    public News convertDTOToEntity(NewsDTO newsDTO) {
        News newsEntity = new News();
        newsEntity.setNewsId(newsDTO.getNewsId());
        newsEntity.setTitle(newsDTO.getTitle());
        newsEntity.setContent(newsDTO.getContent());
        newsEntity.setFile(newsDTO.getFile());
        newsEntity.setPostTime(newsDTO.getPostTime());
        newsEntity.setVisible(newsDTO.getVisible());
        newsEntity.setPublisher(newsDTO.getPublisher());
        return newsEntity;
    }
    @Value("${upload.path}")
    private String uploadPath;
    private String connectStr = "DefaultEndpointsProtocol=https;AccountName=eipulseimages;AccountKey=J3OLfPQvTNjhsavqjIZpktnTy8hCx12b3u6t+IdLYwIJ7i/nzSGJuLgF7Do5/SPoBi5+PkLamBDF+AStcEcIBQ==;EndpointSuffix=core.windows.net";
    private BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();;

  //本地
    public void saveFile(List<MultipartFile> files,Integer newsId) throws IOException {
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String directoryPath = uploadPath + "/" + newsId;
                File directory = new File(directoryPath);

                if (!directory.exists()) {
                    directory.mkdirs(); // 這裡會建立整個路徑，包括中間可能不存在的目錄
                }

                String filePath = directoryPath + "/" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
            }
        }
    }
    
  //雲端
    public String uploadNewsImage(List<MultipartFile> files,Integer newsId) {
        // 獲取 Blob 容器的引用
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("images/news/"+newsId);
        // 獲取 Blob 的引用
        for (MultipartFile file : files) {
            BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());
            try {
                // 上傳文件到 Blob
                blobClient.upload(file.getInputStream(), file.getSize(), true);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error occurred while uploading the file.";
            }
        }

        // 返回 Blob 的 URL
//        System.out.println(blobClient.getBlobUrl());
        return null;
    }

}
