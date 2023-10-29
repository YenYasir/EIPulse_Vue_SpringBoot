package com.eipulse.common.core.domain.entity;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.annotation.Excel.ColumnType;
import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 角色表 sys_role
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Excel(name = "角色序號", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;

	/**
	 * 角色名稱
	 */
	@Excel(name = "角色名稱")
	@Column(name = "role_name")
	private String roleName;

	/**
	 * 角色權限
	 */
	@Excel(name = "角色權限")
	@Column(name = "role_key")
	private String roleKey;

	/**
	 * 角色排序
	 */
	@Excel(name = "角色排序")
	@Column(name = "role_sort")
	private String roleSort;

	/**
	 * 數據範圍（1：所有數據權限；2：自訂數據權限；3：本部門數據權限；4：本部門及以下數據權限）
	 */
	@Excel(name = "數據範圍", readConverterExp = "1=所有數據權限,2=自訂數據權限,3=本部門數據權限,4=本部門及以下數據權限")
	@Column(name = "data_scope")
	private String dataScope;

	/**
	 * 菜單樹選擇項是否關聯顯示（ 0：父子不互相關聯顯示 1：父子互相關聯顯示）
	 */
	@Column(name = "menu_check_strictly")
	private boolean menuCheckStrictly;

	/**
	 * 部門樹選擇項是否關聯顯示（0：父子不互相關聯顯示 1：父子互相關聯顯示 ）
	 */
	@Column(name = "dept_check_strictly")
	private boolean deptCheckStrictly;

	/**
	 * 角色狀態（0正常 1停用）
	 */
	@Excel(name = "角色狀態", readConverterExp = "0=正常,1=停用")
	@Column(name = "status")
	private String status;

	/**
	 * 刪除標誌（0代表存在 2代表刪除）
	 */
	@Column(name = "del_flag")
	private String delFlag;

	/**
	 * 創建者
	 */
	@Column(name = "create_by")
	private String createBy;

	/**
	 * 創建時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 更新者
	 */
	@Column(name = "update_by")
	private String updateBy;

	/**
	 * 更新時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 備註
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 員工是否存在此角色標識 默認不存在
	 */
	@Transient
	private boolean flag = false;

	/**
	 * 菜單組
	 */
	@Transient
	private Long[] menuIds;

	/**
	 * 部門組（數據權限）
	 */
	@Transient
	private Long[] deptIds;

	public SysRole() {

	}

	public SysRole(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isAdmin() {
		return isAdmin(this.roleId);
	}

	public static boolean isAdmin(Long roleId) {
		return roleId != null && 1L == roleId;
	}

	@NotBlank(message = "角色名稱不能為空")
	@Size(min = 0, max = 30, message = "角色名稱長度不能超過30個字元")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@NotBlank(message = "權限字元不能為空")
	@Size(min = 0, max = 100, message = "權限字元長度不能超過100個字元")
	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	@NotBlank(message = "顯示順序不能為空")
	public String getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(String roleSort) {
		this.roleSort = roleSort;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public boolean isMenuCheckStrictly() {
		return menuCheckStrictly;
	}

	public void setMenuCheckStrictly(boolean menuCheckStrictly) {
		this.menuCheckStrictly = menuCheckStrictly;
	}

	public boolean isDeptCheckStrictly() {
		return deptCheckStrictly;
	}

	public void setDeptCheckStrictly(boolean deptCheckStrictly) {
		this.deptCheckStrictly = deptCheckStrictly;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long[] getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Long[] menuIds) {
		this.menuIds = menuIds;
	}

	public Long[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(Long[] deptIds) {
		this.deptIds = deptIds;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SysRole{" + "roleId=" + roleId + ", roleName='" + roleName + '\'' + ", roleKey='" + roleKey + '\''
				+ ", roleSort='" + roleSort + '\'' + ", dataScope='" + dataScope + '\'' + ", menuCheckStrictly="
				+ menuCheckStrictly + ", deptCheckStrictly=" + deptCheckStrictly + ", status='" + status + '\''
				+ ", delFlag='" + delFlag + '\'' + ", createBy='" + createBy + '\'' + ", createTime=" + createTime
				+ ", updateBy='" + updateBy + '\'' + ", updateTime=" + updateTime + ", remark='" + remark + '\''
				+ ", flag=" + flag + ", menuIds=" + Arrays.toString(menuIds) + ", deptIds=" + Arrays.toString(deptIds)
				+ '}';
	}
}
