package com.eipulse.teamproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.entitys.OfficeRegions;
import com.eipulse.teamproject.serviceimp.OfficeRegionsServiceImp;

@RestController
public class OfficeRegionsController {

	private OfficeRegionsServiceImp officeRegionsServiceImp;

	@Autowired
	public OfficeRegionsController(OfficeRegionsServiceImp officeRegionsServiceImp) {
		this.officeRegionsServiceImp = officeRegionsServiceImp;
	}

//OK
	@PostMapping("/officeRegions/post")
	public OfficeRegions postOfficeRegions(@RequestBody OfficeRegions officeRegions) {

		return officeRegionsServiceImp.saveRegions(officeRegions);
	}

//    ok
	@GetMapping("/officeRegions/all")
	public List<OfficeRegions> findAllOfficeRegions() {
		return officeRegionsServiceImp.findAllRegions();
	}

}