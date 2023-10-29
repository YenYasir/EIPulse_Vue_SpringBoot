package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

import lombok.Data;

/**
 * 員工和職位關聯 sys_user_post 聯合主鍵
 */
@Data
public class SysUserPostKey implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 員工ID
	 */
	private Long userId;

	/**
	 * 職位ID
	 */
	private Long postId;

}
