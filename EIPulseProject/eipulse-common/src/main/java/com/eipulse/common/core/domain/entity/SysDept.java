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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部門表 sys_dept
 */
@Entity
@Table(name = "sys_dept")
@EqualsAndHashCode(callSuper = false)
@Data
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
	 * 聯絡電話
	 */
	@Size(min = 0, max = 11, message = "聯絡電話長度不能超過11個字元")
	@Column(name = "phone")
	private String phone;

	/**
	 * 信箱
	 */
	@Email(message = "信箱格式不正確")
	@Size(min = 0, max = 50, message = "信箱長度不能超過50個字元")
	@Column(name = "email")
	private String email;

	/**
	 * 部門狀態:0正常,1停用
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 刪除標誌（0代表存在 2代表刪除）
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

}
