package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.common.utils.ValidateUtil;

/**
 * 部門管理 數據層
 *
 * @author eipulse
 */
@Repository
public class SysDeptDao extends BaseDao<SysDept, Long> {
	/**
	 * 查詢部門管理數據
	 *
	 * @param dept 部門訊息
	 * @return 部門訊息集合
	 */
	public List<SysDept> findDeptList(SysDept dept) {
		String hql = "from SysDept where delFlag='0' ";
		if (dept.getDeptId() != null && dept.getDeptId() != 0) {
			hql += " and deptIdId= " + dept.getDeptId();
		}
		if (dept.getParentId() != null && dept.getParentId() != 0) {
			hql += " and parentId= " + dept.getParentId();
		}
		if (!ValidateUtil.isEmpty(dept.getDeptName())) {
			hql += " and deptName like '%" + dept.getDeptName() + "%'";
		}
		if (!ValidateUtil.isEmpty(dept.getStatus())) {
			hql += " and status= " + dept.getStatus();
		}
		return this.find(hql);
	}

	/**
	 * 根據角色ID查詢部門樹訊息
	 *
	 * @param roleId            角色ID
	 * @param deptCheckStrictly 部門樹選擇項是否關聯顯示
	 * @return 選中部門列表
	 */
	public List<Long> findDeptListByRoleId(Long roleId, boolean deptCheckStrictly) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select  d.deptId from SysDept d ");
		hql.append(" left join SysRoleDept rd on d.deptId = rd.deptId ");
		hql.append(" where rd.roleId = ?1");
		if (deptCheckStrictly) {
			hql.append(
					" and d.deptId not in (select d.parentId from SysDept d inner join SysRoleDept rd on d.deptId = rd.deptId and rd.roleId = ?2")
					.append(" )");
		}
		hql.append(" order by d.parentId desc, d.orderNum desc ");
		Object param[] = new Object[2];
		param[0] = roleId;
		param[1] = roleId;
		return this.find(hql.toString(), param);
	}

	/**
	 * 根據ID查詢所有子部門
	 *
	 * @param deptId 部門ID
	 * @return 部門列表
	 */
	public List<SysDept> findChildrenDeptById(Long deptId) {
		String sql = "select * from sys_dept where find_in_set(" + deptId + ", ancestors)";
		return this.findBySQL(sql);
	}

	/**
	 * 根據ID查詢所有子部門（正常狀態）
	 *
	 * @param deptId 部門ID
	 * @return 子部門數
	 */
	public int selectNormalChildrenDeptById(Long deptId) {
		String sql = "select count(1) from sys_dept where status = 0 and del_flag = '0' and find_in_set(" + deptId
				+ ", ancestors)";
		List list = this.findByFreeSQL(sql);
		int total = ((Long) list.get(0)).intValue();
		return total;
	}

	/**
	 * 是否存在子節點
	 *
	 * @param deptId 部門ID
	 * @return 結果
	 */
	public int hasChildByDeptId(Long deptId) {
		String hql = "select count(*) from SysDept  where delFlag = '0' and parentId=" + deptId;
		Long total = (Long) this.find(hql).get(0);
		return total.intValue();
	}

	/**
	 * 查詢部門是否存在用戶
	 *
	 * @param deptId 部門ID
	 * @return 結果
	 */
	public int checkDeptExistUser(Long deptId) {
		return 1;
	}

	/**
	 * 通過部門名稱與父部門ID查詢
	 *
	 * @param deptName 部門名稱
	 * @param parentId 父部門ID
	 * @return 結果
	 */
	public List<SysDept> findByParentIdAndDeptName(String deptName, Long parentId) {
		String hql = "from SysDept where parentId=" + parentId + " and deptName=" + FormatUtil.formatStrForDB(deptName);
		return find(hql);
	}

	/**
	 * 修改所在部門正常狀態
	 *
	 * @param deptIds 部門ID組
	 */
	public void updateDeptStatusNormal(Long[] deptIds) {
		String hql = "update SysDept set status = '0' where deptId in " + ConvertUtil.toDbString(deptIds);
		this.executeHql(hql);
	}

	/**
	 * 修改子元素關係
	 *
	 * @param depts 子元素
	 * @return 結果
	 */
	public int updateDeptChildren(List<SysDept> depts) {
		return 1;
	}

}
