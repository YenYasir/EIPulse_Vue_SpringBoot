package com.eipulse.teamproject.dto;

import lombok.Getter;

@Getter
public class NewPasswordDTO {
	private Integer empId;
	private String newPassword;
	private Integer otpCheck;

	public NewPasswordDTO() {
	}

	@Override
	public String toString() {
		return "NewPasswordDTO{" + "empId=" + empId + ", newPassword='" + newPassword + '\'' + ", otpCheck=" + otpCheck
				+ '}';
	}
}