package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.annotation.DataScope;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.domain.TreeSelect;
import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.code.CommonBizCode;
import com.eipulse.system.dao.SysDeptDao;
import com.eipulse.system.dao.SysRoleDao;
import com.eipulse.system.service.ISysDeptService;

/**
 * 部門管理 服務實現
 */
@Transactional(readOnly = true)
@Service
public class SysDeptServiceImpl implements ISysDeptService {

	@Autowired
	private SysDeptDao sysDeptDao;

	@Autowired
	private SysRoleDao sysRoleDao;

	/**
	 * 查詢部門管理數據
	 *
	 * @param req 部門資訊
	 * @return 部門資訊集合
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<SysDept> selectDeptList(SysDept req) {
		List<SysDept> sysDepts = sysDeptDao.findDeptList(req);
		return sysDepts;
	}

	/**
	 * 構建前端所需要樹結構
	 *
	 * @param depts 部門列表
	 * @return 樹結構列表
	 */
	@Override
	public List<SysDept> buildDeptTree(List<SysDept> depts) {
		List<SysDept> returnList = new ArrayList<>();
		List<Long> tempList = new ArrayList<>();
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
	@Override
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
	@Override
	public List<Long> selectDeptListByRoleId(Long roleId) {
		SysRole role = sysRoleDao.findByRoleId(roleId);
		List<Long> ret = sysDeptDao.findDeptListByRoleId(roleId, role.isDeptCheckStrictly());
		return ret;
	}

	/**
	 * 根據部門ID查詢資訊
	 *
	 * @param deptId 部門ID
	 * @return 部門資訊
	 */
	@Override
	public SysDept selectDeptById(Long deptId) {
		SysDept sysDept = sysDeptDao.findById(deptId).orElse(new SysDept());
		return sysDept;
	}

	/**
	 * 根據ID查詢所有子部門（正常狀態）
	 *
	 * @param deptId 部門ID
	 * @return 子部門數
	 */
	@Override
	public int selectNormalChildrenDeptById(Long deptId) {
		int count = sysDeptDao.findNormalChildrenDeptById(deptId);
		return count;
	}

	/**
	 * 是否存在子節點
	 *
	 * @param deptId 部門ID
	 * @return 結果
	 */
	@Override
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
	@Override
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
	@Override
	public String checkDeptNameUnique(SysDept dept) {
		Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
		List<SysDept> sysDepts = sysDeptDao.findByParentIdAndDeptName(dept.getParentId(), dept.getDeptName());
		if (!sysDepts.isEmpty() && sysDepts.get(0).getDeptId().longValue() != deptId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 新增保存部門資訊
	 *
	 * @param dept 部門資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertDept(SysDept dept) {
		SysDept info = sysDeptDao.findById(dept.getParentId()).orElse(new SysDept());
		// 如果父節點不為正常狀態,則不允許新增子節點
		if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
			throw new CustomException("部門停用，不允許新增");
		}
		dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		dept.setDelFlag(CommonBizCode.DelFlag.NO.getCode());
		dept.setCreateTime(new Date());
		sysDeptDao.save(dept);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改保存部門資訊
	 *
	 * @param dept 部門資訊
	 * @return 結果
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public int updateDept(SysDept dept) {
		SysDept newParentDept = sysDeptDao.findById(dept.getParentId()).orElse(new SysDept());
		SysDept oldDept = sysDeptDao.findById(dept.getDeptId()).orElse(new SysDept());
		if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
			String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
			String oldAncestors = oldDept.getAncestors();
			dept.setAncestors(newAncestors);
			updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
		}
		sysDeptDao.save(dept);
		if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
			// 如果該部門是啟用狀態，則啟用該部門的所有上級部門
			updateParentDeptStatus(dept);
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改該部門的父級部門狀態
	 *
	 * @param dept 當前部門
	 */
	private void updateParentDeptStatus(SysDept dept) {
		String updateBy = dept.getUpdateBy();
		dept = sysDeptDao.findById(dept.getDeptId()).orElse(new SysDept());
		dept.setUpdateBy(updateBy);
		sysDeptDao.save(dept);
	}

	/**
	 * 修改子元素關係
	 *
	 * @param deptId       被修改的部門ID
	 * @param newAncestors 新的父ID集合
	 * @param oldAncestors 舊的父ID集合
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
		List<SysDept> children = sysDeptDao.findChildrenDeptById(deptId);
		for (SysDept child : children) {
			child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
		}
		if (children.size() > 0) {
			sysDeptDao.saveAll(children);
		}
	}

	/**
	 * 刪除部門管理資訊
	 *
	 * @param deptId 部門ID
	 * @return 結果
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public int deleteDeptById(Long deptId) {
		sysDeptDao.deleteById(deptId);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 遞迴列表
	 */
	private void recursionFn(List<SysDept> list, SysDept t) {
		// 得到子節點列表
		List<SysDept> childList = getChildList(list, t);
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
	private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
		List<SysDept> tlist = new ArrayList<>();
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
		return getChildList(list, t).size() > 0 ? true : false;
	}
}
