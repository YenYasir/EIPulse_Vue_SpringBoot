package com.eipulse.system.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.annotation.DataScope;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.code.CommonBizCode;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.system.dao.SysRoleDao;
import com.eipulse.system.dao.SysRoleDeptDao;
import com.eipulse.system.dao.SysRoleMenuDao;
import com.eipulse.system.dao.SysUserRoleDao;
import com.eipulse.system.domain.SysRoleDept;
import com.eipulse.system.domain.SysRoleMenu;
import com.eipulse.system.service.ISysRoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * 角色 業務層處理
 */
@Slf4j
@Transactional(readOnly = true)
@Service
public class SysRoleServiceImpl implements ISysRoleService {

	@Qualifier("microSvcExecutor")
	@Autowired
	private ExecutorService executorService;

	@Autowired
	private SysRoleDao roleDao;

	@Autowired
	private SysRoleMenuDao roleMenuDao;

	@Autowired
	private SysUserRoleDao userRoleDao;

	@Autowired
	private SysRoleDeptDao roleDeptDao;

	@Autowired
	private EntityManager entityManager;

	/**
	 * 根據條件分頁查詢角色數據
	 *
	 * @param req 角色資訊
	 * @return 角色數據集合資訊
	 */
	@Override
	@DataScope(deptAlias = "d")
	public Page<SysRole> findPage(SysRole req) {
		PageDomain pageReq = TableSupport.buildPageRequest();
		Page<SysRole> page = roleDao.findMixPage(req, PageRequest.of(pageReq.getPageNum() - 1, pageReq.getPageSize()));
		return page;

	}

	@Override
	public List<SysRole> selectRoleList(SysRole role) {
		return roleDao.findAll();
	}

	/**
	 * 根據員工ID查詢權限
	 *
	 * @param userId 員工ID
	 * @return 權限列表
	 */
	@Override
	public Set<String> selectRolePermissionByUserId(Long userId) {
		List<SysRole> perms = roleDao.findRolePermissionByUserId(userId);
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
	@Override
	public List<SysRole> selectRoleAll() {
		return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
	}

	/**
	 * 根據員工ID獲取角色選擇框列表
	 *
	 * @param userId 員工ID
	 * @return 選中角色ID列表
	 */
	@Override
	public List<Long> selectRoleListByUserId(Long userId) {
		return roleDao.findRoleListByUserId(userId);
	}

	/**
	 * 通過角色ID查詢角色
	 *
	 * @param roleId 角色ID
	 * @return 角色物件資訊
	 */
	@Override
	public SysRole selectRoleById(Long roleId) {
		return roleDao.findById(roleId).orElse(new SysRole());
	}

	/**
	 * 校驗角色名稱是否唯一
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Override
	public String checkRoleNameUnique(SysRole role) {
		Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		List<SysRole> roles = roleDao.findByRoleName(role.getRoleName());

		if (!roles.isEmpty() && roles.get(0).getRoleId().longValue() != roleId.longValue()) {
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
	@Override
	public String checkRoleKeyUnique(SysRole role) {
		Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		List<SysRole> roles = roleDao.findByRoleKey(role.getRoleKey());
		if (!roles.isEmpty() && roles.get(0).getRoleId().longValue() != roleId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗角色是否允許操作
	 *
	 * @param role 角色資訊
	 */
	@Override
	public void checkRoleAllowed(SysRole role) {
		if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
			throw new CustomException("不允許操作超級管理員角色");
		}
	}

	/**
	 * 透過角色ID查詢角色使用數量
	 *
	 * @param roleId 角色ID
	 * @return 結果
	 */
	@Override
	public int countUserRoleByRoleId(Long roleId) {
		return userRoleDao.countUserRoleByRoleId(roleId);
	}

	/**
	 * 新增保存角色資訊
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertRole(SysRole role) {
		// 新增角色資訊
		role.setCreateTime(new Date());
		role.setDelFlag(CommonBizCode.DelFlag.NO.getCode());
		roleDao.save(role);
		return insertRoleMenu(role);
	}

	/**
	 * 修改保存角色資訊
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateRole(SysRole role) {
		// 修改角色資訊
		roleDao.save(role);
		// 刪除角色與菜單關聯
		List<SysRoleMenu> byRoleId = roleMenuDao.findByRoleId(role.getRoleId());
		if (!byRoleId.isEmpty()) {
			roleMenuDao.deleteInBatch(byRoleId);
		}
		return insertRoleMenu(role);
	}

	/**
	 * 修改角色狀態
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateRoleStatus(SysRole role) {
		SysRole sysRole = roleDao.findById(role.getRoleId()).get();
		sysRole.setStatus(role.getStatus());
		sysRole.setUpdateBy(role.getUpdateBy());
		sysRole.setUpdateTime(new Date());
		roleDao.save(sysRole);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改數據權限資訊
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int authDataScope(SysRole role) {
		// 修改角色資訊
		roleDao.save(role);
		// 刪除角色與部門關聯
		List<SysRoleDept> existRoleDept = roleDeptDao.findByRoleId(role.getRoleId());
		if (!existRoleDept.isEmpty()) {
			roleDeptDao.deleteInBatch(existRoleDept);
		}
		// 新增角色和部門資訊（數據權限）
		return insertRoleDept(role);
	}

	/**
	 * 新增角色菜單資訊
	 *
	 * @param role 角色物件
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertRoleMenu(SysRole role) {
		// 新增員工與角色管理
		List<SysRoleMenu> list = new ArrayList<>();
		for (Long menuId : role.getMenuIds()) {
			SysRoleMenu rm = new SysRoleMenu();
			rm.setRoleId(role.getRoleId());
			rm.setMenuId(menuId);
			list.add(rm);
		}
		if (list.size() > 0) {
			roleMenuDao.saveAll(list);
			// TODO 批次保存緩慢sql 待處理
//            Function<Object, Object> saveFunction = bean -> roleMenuDao.save((SysRoleMenu) bean);
//            batchSave(list, saveFunction, executorService);
//            batchSave2(list);
//            batchInsertIk(list);
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	@Autowired
	public JdbcTemplate jdbcTemplate;

	public Integer batchInsertIk(List<SysRoleMenu> ikList) {
		String sql = "insert into sys_role_dept(role_id, dept_id) values(?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Long roleId = ikList.get(i).getRoleId();
				Long menuId = ikList.get(i).getMenuId();
				ps.setLong(1, roleId);
				ps.setLong(2, menuId);
			}

			@Override
			public int getBatchSize() {
				return ikList.size();
			}
		});
		return 0;
	}

	private void batchSave(List<SysRoleMenu> rows, Function<Object, Object> rowFunction,
			ExecutorService excelExecutor) {
		Function<Object, CompletableFuture> rowFutureFunction = row -> CompletableFuture
				.supplyAsync(() -> rowFunction.apply(row), excelExecutor);
		CompletableFuture[] futures = rows.stream().map(rowFutureFunction::apply)
				.toArray(size -> new CompletableFuture[size]);
		CompletableFuture.allOf(futures).join();
	}

	public void batchSave2(List<SysRoleMenu> list) {
		int j = 0;
		for (SysRoleMenu entity : list) {
			// TODO 判斷數據的合法性
			entityManager.persist(entity);
			j++;
			if (j % 50 == 0 || j == list.size()) {
				try {
					entityManager.flush();
				} catch (Exception e) {
					log.error("fail", e);
				} finally {
					entityManager.clear();
				}

			}

		}
	}

	/**
	 * 新增角色部門資訊(數據權限)
	 *
	 * @param role 角色物件
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertRoleDept(SysRole role) {
		// 新增角色與部門（數據權限）管理
		List<SysRoleDept> list = new ArrayList<>();
		for (Long deptId : role.getDeptIds()) {
			SysRoleDept rd = new SysRoleDept();
			rd.setRoleId(role.getRoleId());
			rd.setDeptId(deptId);
			list.add(rd);
		}
		if (list.size() > 0) {
			roleDeptDao.saveAll(list);
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 通過角色ID刪除角色
	 *
	 * @param roleId 角色ID
	 * @return 結果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteRoleById(Long roleId) {
		// 刪除角色與菜單關聯
		List<SysRoleMenu> byRoleId = roleMenuDao.findByRoleId(roleId);
		if (!byRoleId.isEmpty()) {
			roleMenuDao.deleteInBatch(byRoleId);
		}
		// 刪除角色與部門關聯
		List<SysRoleDept> existsRoleDept = roleDeptDao.findByRoleId(roleId);
		if (!existsRoleDept.isEmpty()) {
			roleDeptDao.deleteInBatch(existsRoleDept);
		}
		if (roleDao.existsById(roleId)) {
			roleDao.deleteByRoleId(roleId);
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 批次刪除角色資訊
	 *
	 * @param roleIds 需要刪除的角色ID
	 * @return 結果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteRoleByIds(Long[] roleIds) {
		for (Long roleId : roleIds) {
			checkRoleAllowed(new SysRole(roleId));
			SysRole role = selectRoleById(roleId);
			if (countUserRoleByRoleId(roleId) > 0) {
				throw new CustomException(String.format("%1$s已分配,不能刪除", role.getRoleName()));
			}
		}
		// 刪除角色與菜單關聯
		roleMenuDao.deleteByRoleIdIn(Arrays.asList(roleIds));
		// 刪除角色與部門關聯
		roleDeptDao.deleteByRoleIdIn(Arrays.asList(roleIds));
		roleDao.deleteByRoleIdIn(Arrays.asList(roleIds));
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}
}
