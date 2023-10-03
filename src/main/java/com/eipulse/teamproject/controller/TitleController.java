package com.eipulse.teamproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.entitys.Title;
import com.eipulse.teamproject.repository.TitleRepository;

@RestController
public class TitleController {

	@Autowired
	private TitleRepository tRepo;

	@PostMapping("/titleAdd")
	public Title addTitle(@RequestParam String title) {
		Title newtitle = new Title();
		newtitle.setTitleName(title);

		return tRepo.save(newtitle);
	}
}
