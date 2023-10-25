package com.eipulse.system.domain;

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
 * 職位表 sys_post
 *
 * @author eipulse
 */
@Entity
@Table(name = "sys_post")
public class SysPost extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 職位代號
	 */
	@Excel(name = "職位代號", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	/**
	 * 職位編碼
	 */
	@Excel(name = "職位編碼")
	@NotBlank(message = "職位編碼不能為空")
	@Size(min = 0, max = 64, message = "職位編碼長度不能超過64個字符")
	@Column(name = "post_code")
	private String postCode;

	/**
	 * 職位名稱
	 */
	@Excel(name = "職位名稱")
	@NotBlank(message = "職位名稱不能為空")
	@Size(min = 0, max = 50, message = "職位名稱長度不能超過50個字符")
	@Column(name = "post_name")
	private String postName;

	/**
	 * 職位排序
	 */
	@Excel(name = "職位排序")
	@NotBlank(message = "顯示順序不能為空")
	@Column(name = "post_sort")
	private String postSort;

	/**
	 * 狀態（0正常 1停用）
	 */
	@Excel(name = "狀態", readConverterExp = "0=正常,1=停用")
	@Column(name = "status")
	private String status;

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
	 * 員工是否存在此職位標識 默認不存在
	 */
	@Transient
	private boolean flag = false;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostSort() {
		return postSort;
	}

	public void setPostSort(String postSort) {
		this.postSort = postSort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}