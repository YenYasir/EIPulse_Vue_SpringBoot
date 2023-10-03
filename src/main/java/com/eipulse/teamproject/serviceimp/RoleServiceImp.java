package com.eipulse.teamproject.serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.teamproject.entitys.Role;
import com.eipulse.teamproject.repository.RoleRepository;
import com.eipulse.teamproject.service.RoleService;

@Transactional
@Service
public class RoleServiceImp implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void addRole(Role role) {
		roleRepository.save(role);
	}

}
