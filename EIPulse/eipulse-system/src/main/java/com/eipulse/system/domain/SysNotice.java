package com.eipulse.system.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 通知公告表 sys_notice
 *
 * @author eipulse
 */
@Entity
@Table(name = "sys_notice")
public class SysNotice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 公告ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long noticeId;

	/**
	 * 公告標題
	 */
	@NotBlank(message = "公告標題不能為空")
	@Size(min = 0, max = 50, message = "公告標題不能超過50個字符")
	@Column(name = "notice_title")
	private String noticeTitle;

	/**
	 * 公告類型（1通知 2公告）
	 */
	@Column(name = "notice_type")
	private String noticeType;

	/**
	 * 公告內容
	 */
	@Column(name = "notice_content")
	private String noticeContent;

	/**
	 * 公告狀態（0正常 1關閉）
	 */
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

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
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
}