package com.eipulse.system.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eipulse.common.annotation.DataScope;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.domain.TreeSelect;
import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.core.text.Convert;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.system.dao.SysDeptDao;
import com.eipulse.system.dao.SysRoleDao;

/**
 * 部門管理 服務實現
 * 
 * @author eipulse
 */
@Service
public class SysDeptService extends BaseService<SysDept, Long> {
	@Resource
	private SysDeptDao sysDeptDao;

	@Resource
	private SysRoleDao sysRoleDao;

	@Override
	protected BaseDao<SysDept, Long> getDao() {
		return sysDeptDao;
	}

	/**
	 * 查詢部門管理數據
	 * 
	 * @param dept 部門資訊
	 * @return 部門資訊集合
	 */
	@DataScope(deptAlias = "d")
	public List<SysDept> findDeptList(SysDept dept) {
		return sysDeptDao.findDeptList(dept);
	}

	/**
	 * 構建前端所需要樹結構
	 * 
	 * @param depts 部門列表
	 * @return 樹結構列表
	 */
	public List<SysDept> buildDeptTree(List<SysDept> depts) {
		List<SysDept> returnList = new ArrayList<SysDept>();
		List<Long> tempList = new ArrayList<Long>();
		for (SysDept dept : depts) {
			tempList.add(dept.getDeptId());
		}
		for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();) {
			SysDept dept = iterator.next();
			// 如果是頂級節點, 遍歷該父節點的所有子節點
			if (!tempList.contains(dept.getParentId())) {
				recursionFn(depts, dept);
				returnList.add(dept);
			}
		}
		if (returnList.isEmpty()) {
			returnList = depts;
		}
		return returnList;
	}

	/**
	 * 構建前端所需要下拉樹結構
	 * 
	 * @param depts 部門列表
	 * @return 下拉樹結構列表
	 */
	public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
		List<SysDept> deptTrees = buildDeptTree(depts);
		return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 根據角色ID查詢部門樹資訊
	 * 
	 * @param roleId 角色ID
	 * @return 選中部門列表
	 */
	public List<Long> findDeptListByRoleId(Long roleId) {
		SysRole role = (SysRole) sysRoleDao.getObject(roleId);
		return sysDeptDao.findDeptListByRoleId(roleId, role.isDeptCheckStrictly());
	}

	/**
	 * 根據ID查詢所有子部門（正常狀態）
	 * 
	 * @param deptId 部門ID
	 * @return 子部門數
	 */
	public int selectNormalChildrenDeptById(Long deptId) {
		return sysDeptDao.selectNormalChildrenDeptById(deptId);
	}

	/**
	 * 是否存在子節點
	 * 
	 * @param deptId 部門ID
	 * @return 結果
	 */
	public boolean hasChildByDeptId(Long deptId) {
		int result = sysDeptDao.hasChildByDeptId(deptId);
		return result > 0 ? true : false;
	}

	/**
	 * 查詢部門是否存在員工
	 * 
	 * @param deptId 部門ID
	 * @return 結果 true 存在 false 不存在
	 */
	public boolean checkDeptExistUser(Long deptId) {
		int result = sysDeptDao.checkDeptExistUser(deptId);
		return result > 0 ? true : false;
	}

	/**
	 * 校驗部門名稱是否唯一
	 * 
	 * @param dept 部門資訊
	 * @return 結果
	 */
	public String checkDeptNameUnique(SysDept dept) {
		Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
		SysDept info = null;
		List<SysDept> list = sysDeptDao.findByParentIdAndDeptName(dept.getDeptName(), dept.getParentId());
		if (!ValidateUtil.isEmpty(list)) {
			info = list.get(0);
		}
		if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗部門是否有數據權限
	 * 
	 * @param deptId 部門id
	 */
	public void checkDeptDataScope(Long deptId) {
		if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
			SysDept dept = new SysDept();
			dept.setDeptId(deptId);
			List<SysDept> depts = SpringUtils.getAopProxy(this).findDeptList(dept);
			if (StringUtils.isEmpty(depts)) {
				throw new ServiceException("沒有權限訪問部門數據！");
			}
		}
	}

	/**
	 * 新增保存部門資訊
	 * 
	 * @param dept 部門資訊
	 * @return 結果
	 */
	public void insertDept(SysDept dept) {
		SysDept info = (SysDept) getObject(dept.getParentId());
		// 如果父節點不為正常狀態,則不允許新增子節點
		if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
			throw new ServiceException("部門停用，不允許新增");
		}
		dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		sysDeptDao.addObject(dept);
	}

	/**
	 * 修改保存部門資訊
	 * 
	 * @param dept 部門資訊
	 * @return 結果
	 */
	public void updateDept(SysDept dept) {
		SysDept newParentDept = (SysDept) sysDeptDao.getObject(dept.getParentId());
		SysDept oldDept = (SysDept) sysDeptDao.getObject(dept.getDeptId());
		if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
			String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
			String oldAncestors = oldDept.getAncestors();
			dept.setAncestors(newAncestors);
			updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
		}
		sysDeptDao.updateObject(dept);
		if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
				&& !StringUtils.equals("0", dept.getAncestors())) {
			// 如果該部門是啟用狀態，則啟用該部門的所有上級部門
			updateParentDeptStatusNormal(dept);
		}
	}

	/**
	 * 修改該部門的父級部門狀態
	 * 
	 * @param dept 當前部門
	 */
	private void updateParentDeptStatusNormal(SysDept dept) {
		String ancestors = dept.getAncestors();
		Long[] deptIds = Convert.toLongArray(ancestors);
		sysDeptDao.updateDeptStatusNormal(deptIds);
	}

	/**
	 * 修改子元素關系
	 * 
	 * @param deptId       被修改的部門ID
	 * @param newAncestors 新的父ID集合
	 * @param oldAncestors 舊的父ID集合
	 */
	public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
		List<SysDept> children = sysDeptDao.findChildrenDeptById(deptId);
		for (SysDept child : children) {
			child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
		}
		if (children.size() > 0) {
			sysDeptDao.updateDeptChildren(children);
		}
	}

	/**
	 * 遞歸列表
	 */
	private void recursionFn(List<SysDept> list, SysDept t) {
		// 得到子節點列表
		List<SysDept> childList = findChildList(list, t);
		t.setChildren(childList);
		for (SysDept tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}

	/**
	 * 得到子節點列表
	 */
	private List<SysDept> findChildList(List<SysDept> list, SysDept t) {
		List<SysDept> tlist = new ArrayList<SysDept>();
		Iterator<SysDept> it = list.iterator();
		while (it.hasNext()) {
			SysDept n = it.next();
			if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判斷是否有子節點
	 */
	private boolean hasChild(List<SysDept> list, SysDept t) {
		return findChildList(list, t).size() > 0 ? true : false;
	}
}
