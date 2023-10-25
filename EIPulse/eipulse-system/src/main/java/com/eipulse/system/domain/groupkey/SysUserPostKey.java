package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

/**
 * 員工和職位關聯 sys_user_post 聯合主鍵
 *
 * @author eipulse
 */

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}
}