package com.ispan.spirngboot3demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.spirngboot3demo.model.Title;
import com.ispan.spirngboot3demo.repository.TitleRepository;

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
