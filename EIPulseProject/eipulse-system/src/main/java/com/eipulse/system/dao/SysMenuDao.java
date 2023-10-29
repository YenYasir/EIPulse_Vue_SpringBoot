package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.common.core.domain.entity.SysMenu;

/**
 * 菜單表 數據層
 */
@Repository
public interface SysMenuDao extends JpaRepository<SysMenu, Long>, JpaSpecificationExecutor<SysMenu>, SysMenuDaoCustom {

	@Query(value = " select count(1) from sys_menu where parent_id =?1   ", nativeQuery = true)
	Integer hasChildByMenuId(Long menuId);

	List<SysMenu> findByMenuNameAndParentId(String menuName, Long parentId);

}
