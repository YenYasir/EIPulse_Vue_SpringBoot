package com.eipulse.system.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.eipulse.system.domain.groupkey.SysUserPostKey;

/**
 * 員工和職位關聯 sys_user_post
 *
 * @author eipulse
 */
@Entity
@Table(name = "sys_user_post")
@IdClass(SysUserPostKey.class)
public class SysUserPost implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 員工ID
	 */
	@Id
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 職位ID
	 */
	@Id
	@Column(name = "post_id")
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