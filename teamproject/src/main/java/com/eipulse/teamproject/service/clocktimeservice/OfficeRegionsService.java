package com.eipulse.teamproject.service.clocktimeservice;

import com.eipulse.teamproject.entity.clocktimeentity.OfficeRegions;
import com.eipulse.teamproject.repository.clocktimerepository.OfficeRegionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfficeRegionsService {
    private static final double EARTH_RADIUS = 6371;
    private OfficeRegionsRepository officeRegionsRepository;
    @Autowired
    public OfficeRegionsService(OfficeRegionsRepository officeRegionsRepository) {
        this.officeRegionsRepository = officeRegionsRepository;
    }

//    哈佛賽公式，計算點對點間距離
    public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // 轉換為公尺
        return distance;
    }

    public OfficeRegions saveRegions(OfficeRegions officeRegions) {
        return officeRegionsRepository.save(officeRegions);
    }



    public List<OfficeRegions> findAllRegions() {
        return officeRegionsRepository.findAll();
    }

    public List<OfficeRegions> findByNameLike(String name) {
        List<OfficeRegions> resultList = officeRegionsRepository.findByRegionsNameLike(name);
        if(resultList!=null){
            return  resultList;
        }
        return null;
    }


//依經緯度模糊查詢距離員工最近的公司
    public OfficeRegions findByLikeRegionsDistance(double latitude, double longitude) {
//       抓取全公司資料
        List<OfficeRegions> allRegions = findAllRegions();
//       排序成Map
        Map<OfficeRegions,Double> distances = new HashMap<>();
        for(OfficeRegions region : allRegions){
//            使用哈佛賽抓取距離最近公司
            double distance = haversineDistance(region.getLatitude(), region.getLongitude(),latitude,longitude);
            distances.put(region,distance);
        }
//        進行距離排序
        List<Map.Entry<OfficeRegions,Double>> sortedList = distances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        OfficeRegions closestRegions = sortedList.get(0).getKey();
        return closestRegions;
    }
}
