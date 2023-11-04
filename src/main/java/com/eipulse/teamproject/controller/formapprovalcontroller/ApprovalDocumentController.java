package com.eipulse.teamproject.controller.formapprovalcontroller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ApprovalDocumentController {

	private int repo;
	
	@GetMapping("/form/applyForDocument")
	public String goDocument() {
		return "form/applyForDocument";
	}
	
	@PostMapping("/upload")
	@ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		file.transferTo(new File("/directory/" + file.getOriginalFilename()));
        return "File uploaded successfully!";
    }
	
}
