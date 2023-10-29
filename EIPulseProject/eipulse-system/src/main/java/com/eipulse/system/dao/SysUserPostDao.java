package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysUserPost;
import com.eipulse.system.domain.groupkey.SysUserPostKey;

/**
 * 員工與職位關聯表 數據層
 */
@Repository
public interface SysUserPostDao
		extends JpaRepository<SysUserPost, SysUserPostKey>, JpaSpecificationExecutor<SysUserPost>, SysMenuDaoCustom {

	@Query(value = "select count(1) from sys_user_post where post_id=?1 ", nativeQuery = true)
	Integer countUserPostById(Long postId);

	void deleteByUserId(Long userId);

	Integer deleteByUserIdIn(List<Long> userId);

	List<SysUserPost> findByUserId(Long userId);

}
