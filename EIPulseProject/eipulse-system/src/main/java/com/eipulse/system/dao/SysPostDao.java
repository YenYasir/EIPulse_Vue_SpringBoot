package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysPost;

/**
 * 職位資訊 數據層
 */
@Repository
public interface SysPostDao extends JpaRepository<SysPost, Long>, JpaSpecificationExecutor<SysPost>, SysPostDaoCustom {

	List<SysPost> findByPostName(String postName);

	List<SysPost> findByPostCode(String postCode);

	void deleteByPostIdIn(List<Long> ids);

	@Query(value = "select p.* from sys_post p" + "   left join sys_user_post up on up.post_id = p.post_id"
			+ "   left join sys_user u on u.user_id = up.user_id" + "   where u.user_name = ?1 ", nativeQuery = true)
	List<SysPost> findPostsByUserName(String userName);

}
