package com.ispan.spirngboot3demo;

import com.ispan.spirngboot3demo.model.PermissionInfo;
import com.ispan.spirngboot3demo.model.PermissionInfoDTO;
import com.ispan.spirngboot3demo.model.TitleDTO;
import com.ispan.spirngboot3demo.repository.PermissionInfoRepository;
import com.ispan.spirngboot3demo.repository.PermissionRepository;
import com.ispan.spirngboot3demo.service.PermisInfoService;
import com.ispan.spirngboot3demo.service.TitleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Spirngboot3demoApplicationTests {

	@Autowired
	private PermissionInfoRepository permissionInfoRepository;
	@Transactional
	@Test
	void contextLoads() {
		List<PermissionInfo> permissionInfos = permissionInfoRepository.findAll();
		List<PermissionInfoDTO> permissionInfoDTOs = new ArrayList<>();

		for (PermissionInfo permissionInfo : permissionInfos) {
			PermissionInfoDTO dto = new PermissionInfoDTO(
					permissionInfo.getId(),
					permissionInfo.getEmp().getEmpId(),
					permissionInfo.getEmp().getEmpName(),
					permissionInfo.getPermission().getPermissionId(),
					permissionInfo.getPermission().getPermissionName()
			);
			permissionInfoDTOs.add(dto);
		}
		System.out.println(permissionInfoDTOs);
	}

}
