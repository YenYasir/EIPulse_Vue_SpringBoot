package com.eipulse.common.core.domain.entity;

import java.util.Arrays;
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
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.annotation.Excel.ColumnType;
import com.eipulse.common.annotation.Excel.Type;
import com.eipulse.common.annotation.Excels;
import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 員工物件 sys_user
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 員工ID
	 */
	@Excel(name = "員工序號", cellType = ColumnType.NUMERIC, prompt = "員工編號")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 部門ID
	 */
	@Excel(name = "部門編號", type = Type.IMPORT)
	@Column(name = "dept_id")
	private Long deptId;

	/**
	 * 員工帳號
	 */
	@Excel(name = "登入名稱")
	@Column(name = "user_name")
	private String userName;

	/**
	 * 員工暱稱
	 */
	@Excel(name = "員工名稱")
	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 員工信箱
	 */
	@Excel(name = "員工信箱")
	@Column(name = "email")
	private String email;

	/**
	 * 手機號碼
	 */
	@Excel(name = "手機號碼")
	@Column(name = "phonenumber")
	private String phonenumber;

	/**
	 * 員工性別
	 */
	@Excel(name = "員工性別", readConverterExp = "0=男,1=女,2=未知")
	@Column(name = "sex")
	private String sex;

	/**
	 * 員工頭像
	 */
	@Column(name = "avatar")
	private String avatar;

	/**
	 * 密碼
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 帳號狀態（0正常 1停用）
	 */
	@Excel(name = "帳號狀態", readConverterExp = "0=正常,1=停用")
	@Column(name = "status")
	private String status;

	/**
	 * 刪除標誌（0代表存在 2代表刪除）
	 */
	@Column(name = "del_flag")
	private String delFlag;

	/**
	 * 最後登入IP
	 */
	@Excel(name = "最後登入IP", type = Type.EXPORT)
	@Column(name = "login_ip")
	private String loginIp;

	/**
	 * 最後登入時間
	 */
	@Excel(name = "最後登入時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	@Column(name = "login_date")
	private Date loginDate;

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
	 * 部門物件
	 */
	@Excels({ @Excel(name = "部門名稱", targetAttr = "deptName", type = Type.EXPORT),
			@Excel(name = "部門負責人", targetAttr = "leader", type = Type.EXPORT) })
	@Transient
	private SysDept dept;

	/**
	 * 角色物件
	 */
	@Transient
	private List<SysRole> roles;

	/**
	 * 鹽加密
	 */
	@Transient
	private String salt;

	/**
	 * 角色組
	 */
	@Transient
	private Long[] roleIds;

	/**
	 * 職位組
	 */
	@Transient
	private Long[] postIds;

	public SysUser() {

	}

	public SysUser(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isAdmin() {
		return isAdmin(this.userId);
	}

	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Size(min = 0, max = 30, message = "員工暱稱長度不能超過30個字元")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@NotBlank(message = "員工帳號不能為空")
	@Size(min = 0, max = 30, message = "員工帳號長度不能超過30個字元")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Email(message = "信箱格式不正確")
	@Size(min = 0, max = 50, message = "信箱長度不能超過50個字元")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Size(min = 0, max = 11, message = "手機號碼長度不能超過11個字元")
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@JsonIgnore
	@JsonProperty
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long[] getPostIds() {
		return postIds;
	}

	public void setPostIds(Long[] postIds) {
		this.postIds = postIds;
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
		return "SysUser{" + "userId=" + userId + ", deptId=" + deptId + ", userName='" + userName + '\''
				+ ", nickName='" + nickName + '\'' + ", email='" + email + '\'' + ", phonenumber='" + phonenumber + '\''
				+ ", sex='" + sex + '\'' + ", avatar='" + avatar + '\'' + ", password='" + password + '\'' + ", salt='"
				+ salt + '\'' + ", status='" + status + '\'' + ", delFlag='" + delFlag + '\'' + ", loginIp='" + loginIp
				+ '\'' + ", loginDate=" + loginDate + ", createBy='" + createBy + '\'' + ", createTime=" + createTime
				+ ", updateBy='" + updateBy + '\'' + ", updateTime=" + updateTime + ", remark='" + remark + '\''
				+ ", dept=" + dept + ", roles=" + roles + ", roleIds=" + Arrays.toString(roleIds) + ", postIds="
				+ Arrays.toString(postIds) + '}';
	}
}
