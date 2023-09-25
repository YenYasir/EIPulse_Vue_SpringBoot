package com.ispan.spirngboot3demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.spirngboot3demo.model.Role;
import com.ispan.spirngboot3demo.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	public void addRole(Role role) {
		roleRepo.save(role);
	}
	
	
}
