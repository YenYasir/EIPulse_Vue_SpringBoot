package com.eipulse.common.core.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 部門表 sys_dept
 * 
 * @author eipulse
 *
 */
@Entity
@Table(name = "sys_dept")
public class SysDept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 部門ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dept_id")
	private Long deptId;

	/**
	 * 父部門ID
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 祖級列表
	 */
	@Column(name = "ancestors")
	private String ancestors;

	/**
	 * 部門名稱
	 */
	@NotBlank(message = "部門名稱不能為空")
	@Size(min = 0, max = 30, message = "部門名稱長度不能超過30個字元")
	@Column(name = "dept_name")
	private String deptName;

	/**
	 * 顯示順序
	 */
	@NotNull(message = "顯示順序不能為空")
	@Column(name = "order_num")
	private Integer orderNum;

	/**
	 * 負責人
	 */
	@Column(name = "leader")
	private String leader;

	/**
	 * 聯系電話
	 */
	@Size(min = 0, max = 11, message = "聯系電話長度不能超過11個字元")
	@Column(name = "phone")
	private String phone;

	/**
	 * 郵箱
	 */
	@Email(message = "郵箱格式不正確")
	@Size(min = 0, max = 50, message = "郵箱長度不能超過50個字元")
	@Column(name = "email")
	private String email;

	/**
	 * 部門狀態:0正常,1停用
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 刪除標志（0代表存在 2代表刪除）
	 */
	@Column(name = "del_flag")
	private String delFlag;

	/**
	 * 父部門名稱
	 */
	@Transient
	private String parentName;

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
	 * 子部門
	 */
	@Transient
	private List<SysDept> children = new ArrayList<>();

	public Long getDeptId() {
		return deptId;
	}

	public Long getParentId() {
		return parentId;
	}

	public String getAncestors() {
		return ancestors;
	}

	public String getDeptName() {
		return deptName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public String getLeader() {
		return leader;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getStatus() {
		return status;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public String getParentName() {
		return parentName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public List<SysDept> getChildren() {
		return children;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setChildren(List<SysDept> children) {
		this.children = children;
	}
}
