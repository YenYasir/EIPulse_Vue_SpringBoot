package com.ispan.spirngboot3demo;

import com.ispan.spirngboot3demo.model.PermissionInfo;
import com.ispan.spirngboot3demo.model.PermissionInfoDTO;
import com.ispan.spirngboot3demo.model.TitleDTO;
import com.ispan.spirngboot3demo.repository.PermissionInfoRepository;
import com.ispan.spirngboot3demo.repository.PermissionRepository;
import com.ispan.spirngboot3demo.repository.ResignRecordRepository;
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
	private ResignRecordRepository resignRecordRepository;
	@Transactional
	@Test
	void contextLoads() {
		System.out.println(resignRecordRepository.findByEmpId(2));
	}

}
