package com.eipulse.teamproject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.entitys.Role;
import com.eipulse.teamproject.repository.RoleRepository;

@RestController
public class InsertRoleController {

	@Autowired
	private RoleRepository roleRepository;

//	@Autowired
//	private RoleService roleService;

	@PostMapping("/emp/insertRole")
	public Role addRole(@RequestParam String rolename, @RequestParam Integer permission) {
		Role role = new Role();
		role.setRole_name(rolename);
		role.setPermission(permission);
		return roleRepository.save(role);
	}

	@DeleteMapping("/emp/deleteRole")
	public String deleteRoleById(@RequestParam Integer id) {
		Optional<Role> optional = roleRepository.findById(id);
		if (optional.isEmpty()) {
			return "沒有這筆資料";
		}

		roleRepository.deleteById(id);

		return "刪除OK";
	}

}
