package com.ispan.spirngboot3demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.spirngboot3demo.model.Role;
import com.ispan.spirngboot3demo.repository.RoleRepository;
import com.ispan.spirngboot3demo.service.RoleService;

@RestController
public class InsertRoleController {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private RoleService roleService;
	
	@PostMapping("/emp/insertRole")
	public Role addRole(@RequestParam String rolename,@RequestParam Integer permission) {
		Role role = new Role();
		role.setRoleName(rolename);
		role.setPermission(permission);
		return roleRepo.save(role);
	}
	@DeleteMapping("/emp/deleteRole")
	public String deleteRoleById(@RequestParam Integer id) {
		Optional<Role> optional = roleRepo.findById(id);
		if(optional.isEmpty()) {
			return "沒有這筆資料";
		}
		
		roleRepo.deleteById(id);
		
		return "刪除OK";
	}

}
