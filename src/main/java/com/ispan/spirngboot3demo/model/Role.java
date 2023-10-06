package com.ispan.spirngboot3demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "permission")
	private Integer permission;

	@Column(name = "permission_statement")
	private  String permissionStatement;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "role", cascade = CascadeType.ALL)
	private List<PermissionInfo> permissionInfo;


}
