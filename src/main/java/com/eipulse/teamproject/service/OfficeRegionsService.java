package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.OfficeRegions;

import java.util.List;

public interface OfficeRegionsService {
    double haversineDistance(double lat1, double lon1, double lat2, double lon2);
    OfficeRegions saveRegions(OfficeRegions officeRegions);

    OfficeRegions findByIdRegions(Integer id);

    List<OfficeRegions> findAllRegions();
    List<OfficeRegions> findByNameLike(String name);

    boolean deleteByIdRegions(Integer id);

    OfficeRegions findByLikeRegionsDistance(double latitude,double longitude);
}
