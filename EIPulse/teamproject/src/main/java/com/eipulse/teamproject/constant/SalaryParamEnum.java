package com.eipulse.teamproject.constant;

public enum SalaryParamEnum {

	WELFARE("福利金", 0.005), // 0.5%
	LABOREE("勞保勞工負擔", 0.2), // 20%
	EMPINSURANCE("就保費率", 0.12), // 12%
	HEALTHINSURANCE("健保費率", 0.0517), // 5.17%
	HEALTHEE("健保勞工負擔", 0.3); // 30%

	private final String name;
	private final Double value;

	SalaryParamEnum(String name, Double value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Double getValue() {
		return value;
	}
}
