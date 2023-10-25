package com.eipulse.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.annotation.DataScope;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.dao.SysRoleDao;
import com.eipulse.system.dao.SysRoleDeptDao;
import com.eipulse.system.dao.SysRoleMenuDao;
import com.eipulse.system.dao.SysUserRoleDao;
import com.eipulse.system.domain.SysRoleDept;
import com.eipulse.system.domain.SysRoleMenu;
import com.eipulse.system.domain.SysUserRole;

/**
 * 角色 業務層處理
 * 
 * @author eipulse
 */
@Service
public class SysRoleService extends BaseService {
	@Autowired
	private SysRoleDao sysRoleDao;

	@Resource
	private SysRoleMenuDao sysRoleMenuDao;

	@Resource
	private SysUserRoleDao sysUserRoleDao;

	@Resource
	private SysRoleDeptDao sysRoleDeptDao;

	@Override
	protected BaseDao getDao() {
		return sysRoleDao;
	}

	/**
	 * 根據條件分頁查詢角色數據
	 * 
	 * @param role 角色資訊
	 * @return 角色數據集合資訊
	 */
	@DataScope(deptAlias = "d")
	public Page findRoleList(SysRole role) {
		return sysRoleDao.findRoleList(role);
	}

	/**
	 * 根據員工ID查詢角色
	 * 
	 * @param userId 員工ID
	 * @return 角色列表
	 */
	public List<SysRole> findRolesByUserId(Long userId) {
		List<SysRole> userRoles = sysRoleDao.findRolePermissionByUserId(userId);
		List<SysRole> roles = findRoleAll();
		for (SysRole role : roles) {
			for (SysRole userRole : userRoles) {
				if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
					role.setFlag(true);
					break;
				}
			}
		}
		return roles;
	}

	/**
	 * 根據員工ID查詢權限
	 * 
	 * @param userId 員工ID
	 * @return 權限列表
	 */
	public Set<String> findRolePermissionByUserId(Long userId) {
		List<SysRole> perms = sysRoleDao.findRolePermissionByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (SysRole perm : perms) {
			if (StringUtils.isNotNull(perm)) {
				permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * 查詢所有角色
	 * 
	 * @return 角色列表
	 */
	public List<SysRole> findRoleAll() {
		return sysRoleDao.findRoleAll();
	}

	/**
	 * 根據員工ID獲取角色選擇框列表
	 * 
	 * @param userId 員工ID
	 * @return 選中角色ID列表
	 */
	public List<Long> findRoleListByUserId(Long userId) {
		return sysRoleDao.findRoleListByUserId(userId);
	}

	/**
	 * 校驗角色名稱是否唯一
	 * 
	 * @param role 角色資訊
	 * @return 結果
	 */
	public String checkRoleNameUnique(SysRole role) {
		Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		SysRole info = sysRoleDao.checkRoleNameUnique(role.getRoleName());
		if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗角色權限是否唯一
	 * 
	 * @param role 角色資訊
	 * @return 結果
	 */
	public String checkRoleKeyUnique(SysRole role) {
		Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		SysRole info = sysRoleDao.checkRoleKeyUnique(role.getRoleKey());
		if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗角色是否允許操作
	 * 
	 * @param role 角色資訊
	 */
	public void checkRoleAllowed(SysRole role) {
		if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
			throw new ServiceException("不允許操作超級管理員角色");
		}
	}

	/**
	 * 校驗角色是否有數據權限
	 * 
	 * @param roleId 角色id
	 */
	public void checkRoleDataScope(Long roleId) {
		if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
			SysRole role = new SysRole();
			role.setRoleId(roleId);
			// List<SysRole> roles = SpringUtils.getAopProxy(this).findRoleList(role);
			// if (StringUtils.isEmpty(roles))
			// {
			// throw new ServiceException("沒有權限訪問角色數據！");
			// }
		}
	}

	/**
	 * 通過角色ID查詢角色使用數量
	 * 
	 * @param roleId 角色ID
	 * @return 結果
	 */
	public int countUserRoleByRoleId(Long roleId) {
		return sysUserRoleDao.countUserRoleByRoleId(roleId);
	}

	/**
	 * 新增保存角色資訊
	 * 
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Transactional
	public int insertRole(SysRole role) {
		// 新增角色資訊
		sysRoleDao.addObject(role);
		return insertRoleMenu(role);
	}

	/**
	 * 修改保存角色資訊
	 * 
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Transactional
	public int updateRole(SysRole role) {
		// 修改角色資訊
		sysRoleDao.updateObject(role);
		// 刪除角色與菜單關聯
		sysRoleMenuDao.deleteRoleMenuByRoleId(role.getRoleId());
		return insertRoleMenu(role);
	}

	/**
	 * 修改角色狀態
	 * 
	 * @param role 角色資訊
	 * @return 結果
	 */
	public void updateRoleStatus(SysRole role) {
		sysRoleDao.updateObject(role);
	}

	/**
	 * 修改數據權限資訊
	 * 
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Transactional
	public void authDataScope(SysRole role) {
		// 修改角色資訊
		sysRoleDao.updateObject(role);
		// 刪除角色與部門關聯
		sysRoleDeptDao.deleteRoleDeptByRoleId(role.getRoleId());
		// 新增角色和部門資訊（數據權限）
		insertRoleDept(role);
	}

	/**
	 * 新增角色菜單資訊
	 * 
	 * @param role 角色物件
	 */
	public int insertRoleMenu(SysRole role) {
		int rows = 1;
		// 新增員工與角色管理
		List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
		for (Long menuId : role.getMenuIds()) {
			SysRoleMenu rm = new SysRoleMenu();
			rm.setRoleId(role.getRoleId());
			rm.setMenuId(menuId);
			list.add(rm);
		}
		if (list.size() > 0) {
			sysRoleMenuDao.saveList(list);
			// rows = roleMenuMapper.batchRoleMenu(list);
		}
		return rows;
	}

	/**
	 * 新增角色部門資訊(數據權限)
	 *
	 * @param role 角色物件
	 */
	public void insertRoleDept(SysRole role) {
		int rows = 1;
		// 新增角色與部門（數據權限）管理
		List<SysRoleDept> list = new ArrayList<SysRoleDept>();
		for (Long deptId : role.getDeptIds()) {
			SysRoleDept rd = new SysRoleDept();
			rd.setRoleId(role.getRoleId());
			rd.setDeptId(deptId);
			list.add(rd);
		}
		if (list.size() > 0) {
			sysRoleDeptDao.saveList(list);
		}
	}

	/**
	 * 通過角色ID刪除角色
	 * 
	 * @param roleId 角色ID
	 * @return 結果
	 */
	@Transactional
	public void deleteRoleById(Long roleId) {
		// 刪除角色與菜單關聯
		sysRoleMenuDao.deleteRoleMenuByRoleId(roleId);
		// 刪除角色與部門關聯
		sysRoleDeptDao.deleteRoleDeptByRoleId(roleId);
		sysRoleDao.deleteObject(roleId);
	}

	/**
	 * 批量刪除角色資訊
	 * 
	 * @param roleIds 需要刪除的角色ID
	 * @return 結果
	 */
	@Transactional
	public void deleteRoleByIds(Long[] roleIds) {
		for (Long roleId : roleIds) {
			checkRoleAllowed(new SysRole(roleId));
			SysRole role = (SysRole) getObject(roleId);
			if (countUserRoleByRoleId(roleId) > 0) {
				throw new ServiceException(String.format("%1$s已分配,不能刪除", role.getRoleName()));
			}
		}
		// 刪除角色與菜單關聯
		sysRoleMenuDao.deleteRoleMenu(roleIds);
		// 刪除角色與部門關聯
		sysRoleDeptDao.deleteRoleDept(roleIds);
		// 刪除角色
		this.deleteObject(roleIds);
	}

	/**
	 * 取消授權員工角色
	 * 
	 * @param userRole 員工和角色關聯資訊
	 * @return 結果
	 */
	public int deleteAuthUser(SysUserRole userRole) {
		return sysUserRoleDao.deleteUserRoleInfo(userRole);
	}

	/**
	 * 批量取消授權員工角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要取消授權的員工數據ID
	 * @return 結果
	 */
	public int deleteAuthUsers(Long roleId, Long[] userIds) {
		return sysUserRoleDao.deleteUserRoleInfos(roleId, userIds);
	}

	/**
	 * 批量選擇授權員工角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要刪除的員工數據ID
	 * @return 結果
	 */
	public void insertAuthUsers(Long roleId, Long[] userIds) {
		// 新增員工與角色管理
		List<SysUserRole> list = new ArrayList<SysUserRole>();
		for (Long userId : userIds) {
			SysUserRole ur = new SysUserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		sysUserRoleDao.saveList(list);
	}

}
