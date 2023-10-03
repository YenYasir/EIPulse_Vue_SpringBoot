package com.eipulse.teamproject.service;

import org.springframework.stereotype.Service;

import com.eipulse.teamproject.entitys.Role;

@Service
public interface RoleService {
	void addRole(Role role);
}
