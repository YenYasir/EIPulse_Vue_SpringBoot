package com.eipulse.teamproject.repository.clocktimerepository;

import com.eipulse.teamproject.entity.clocktimeentity.OfficeRegions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRegionsRepository extends JpaRepository<OfficeRegions, Integer> {
//    likeSearch分公司名
    List<OfficeRegions> findByRegionsNameLike(String name);
}