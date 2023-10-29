package com.eipulse.system.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.common.core.domain.entity.SysUser;

/**
 * 員工表 數據層
 */
@Repository
public interface SysUserDao extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser>, SysUserDaoCustom {

	Optional<SysUser> findByUserName(String userName);

	Optional<SysUser> findByPhonenumber(String phonenumber);

	Optional<SysUser> findByEmail(String email);

	@Modifying
	@Query(value = " update sys_user set avatar = ?1 where user_name = ?2 ", nativeQuery = true)
	Integer updateUserAvatar(String userName, String avatar);

	Integer deleteByUserIdIn(List<Long> userIds);

}
