package com.eipulse.common.core.dto;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

/**
 * 員工對象 sys_user
 */
@Data
public class SysUserDto {

	private BigInteger userId;

	private BigInteger deptId;

	private String nickName;

	private String userName;

	private String email;

	private String avatar;

	private String phonenumber;

	private String password;

	private Character sex;

	private Character status;

	private Character delFlag;

	private String loginIp;

	private Date loginDate;

	private String createBy;

	private Date createTime;

	private Date updateTime;

	private String updateBy;

	private String remark;

	private String deptName;

	private String leader;
}
