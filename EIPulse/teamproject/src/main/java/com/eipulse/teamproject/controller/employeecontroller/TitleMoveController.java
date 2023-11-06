package com.eipulse.teamproject.controller.employeecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.dto.employeedto.TitleMoveDTO;
import com.eipulse.teamproject.service.employeeservice.TitleMoveService;

@RestController
public class TitleMoveController {

	private TitleMoveService moveService;

	@Autowired
	public TitleMoveController(TitleMoveService moveService) {
		this.moveService = moveService;
	}

	// add
	@PostMapping("/TitleMove/add")
	public ResponseEntity<?> add(@RequestBody TitleMoveDTO dto) {
		moveService.add(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// find once
	@GetMapping("/TitleMove/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		return new ResponseEntity<>(moveService.findById(id), HttpStatus.OK);
	}

	// find all
	@GetMapping("/TitleMove/findAll")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(moveService.findAll(), HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/TitleMove/delete{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		moveService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// update
	@PutMapping("/TitleMove/update")
	public ResponseEntity<?> update(@RequestBody TitleMoveDTO moveDTO) {
		return new ResponseEntity<>(moveService.update(moveDTO), HttpStatus.OK);
	}

	// 普通分頁
	@GetMapping("/TitleMove/paged/{pageNumber}")
	@ResponseStatus(HttpStatus.OK)
	public Page<TitleMoveDTO> getMoveByPage(@PathVariable Integer pageNumber) {
		return moveService.findByPage(pageNumber);
	}

	// 模糊搜尋的分頁
	@GetMapping("/TitleMove/paged/{name}/{pageNumber}")
	@ResponseStatus(HttpStatus.OK)
	public Page<TitleMoveDTO> getMoveByNamePage(@PathVariable Integer pageNumber, @PathVariable String name) {
		return moveService.findByNamePage(pageNumber, name);
	}
}
