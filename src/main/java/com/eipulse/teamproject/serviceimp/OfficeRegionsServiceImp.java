package com.eipulse.teamproject.serviceimp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eipulse.teamproject.entitys.OfficeRegions;
import com.eipulse.teamproject.repository.OfficeRegionsRepository;
import com.eipulse.teamproject.service.OfficeRegionsService;

@Service
public class OfficeRegionsServiceImp implements OfficeRegionsService {
	private static final double EARTH_RADIUS = 6371;
	private OfficeRegionsRepository officeRegionsRepository;

	@Autowired
	public OfficeRegionsServiceImp(OfficeRegionsRepository officeRegionsRepository) {
		this.officeRegionsRepository = officeRegionsRepository;
	}

//    哈佛賽公式，計算點對點間距離
	@Override
	public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = EARTH_RADIUS * c * 1000; // 轉換為公尺
		return distance;
	}

	@Override
	public OfficeRegions saveRegions(OfficeRegions officeRegions) {
		return officeRegionsRepository.save(officeRegions);
	}

	@Override
	public OfficeRegions findByIdRegions(Integer id) {
		if (id != null) {
			Optional<OfficeRegions> optional = officeRegionsRepository.findById(id);

			return optional.get();
		}
		return null;
	}

	@Override
	public List<OfficeRegions> findAllRegions() {
		return officeRegionsRepository.findAll();
	}

	@Override
	public List<OfficeRegions> findByNameLike(String name) {
		List<OfficeRegions> resultList = officeRegionsRepository.findByRegionsNameLike(name);
		if (resultList != null) {
			return resultList;
		}
		return null;
	}

	@Override
	public boolean deleteByIdRegions(Integer id) {
		Optional<OfficeRegions> optional = officeRegionsRepository.findById(id);
		if (optional.isPresent()) {
			officeRegionsRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override // like search emp near company
	public OfficeRegions findByLikeRegionsDistance(double latitude, double longitude) {
//        get all company location
		List<OfficeRegions> allRegions = findAllRegions();
//        create key value of regions
		Map<OfficeRegions, Double> distances = new HashMap<>();
		for (OfficeRegions region : allRegions) {
//            use haversine check emp with everyone company distance
			double distance = haversineDistance(region.getLatitude(), region.getLongitude(), latitude, longitude);
			distances.put(region, distance);
		}
//        sort distance
		List<Map.Entry<OfficeRegions, Double>> sortedList = distances.entrySet().stream()
				.sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
		OfficeRegions closestRegions = sortedList.get(0).getKey();
		return closestRegions;
	}
}
