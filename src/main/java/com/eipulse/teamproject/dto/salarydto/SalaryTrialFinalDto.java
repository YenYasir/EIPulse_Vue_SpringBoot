package com.eipulse.teamproject.dto.salarydto;

import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import com.eipulse.teamproject.entity.salaryentity.SalaryMonthRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SalaryTrialFinalDto {
	
	
	private SalaryMonthRecordDto salaryMonthRecordDto;
	
	private List<SalaryDetailDto> salaryDetailDtos;
	


	public SalaryTrialFinalDto() {
		// TODO Auto-generated constructor stub
	}
	
	public SalaryTrialFinalDto(SalaryMonthRecordDto salaryMonthRecordDto,SalaryDetailDto salaryDetailDto) {
		// TODO Auto-generated constructor stub
	}
	
	// 傳入 薪資月紀錄表與明細表
	public SalaryTrialFinalDto(SalaryMonthRecord salaryRecord, List<SalaryDetail> salaryDetails) {
		this.salaryMonthRecordDto = new SalaryMonthRecordDto(salaryRecord);
		
		// 賦值
		List<SalaryDetailDto> salaryDetailDtos= new ArrayList<>();
		for(SalaryDetail detail:salaryDetails) {
			SalaryDetailDto detailDto = new SalaryDetailDto(detail);
			salaryDetailDtos.add(detailDto);			
		}
		
		this.salaryDetailDtos = salaryDetailDtos;
	}

	
	
	public SalaryTrialFinalDto(List<SalaryDetail> salaryDetails) {
		List<SalaryDetailDto> salaryDetailDtos= new ArrayList<>();
		for(SalaryDetail detail:salaryDetails) {
			SalaryDetailDto detailDto = new SalaryDetailDto(detail);
			salaryDetailDtos.add(detailDto);			
		}
		this.salaryDetailDtos = salaryDetailDtos;
	}
}
	


	

