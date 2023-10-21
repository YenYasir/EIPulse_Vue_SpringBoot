package com.eipulse.teamproject.dto;

import java.time.LocalDateTime;

import com.eipulse.teamproject.entitys.Leave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveDTO {

  private Integer formId;
  
  private Integer typeId;

  private String reason;

  private Integer days;

  private Integer hours;

  private LocalDateTime startTime;

  private byte[] file;

	  public LeaveDTO(Leave leave) {
			
		this.formId = leave.getFormId();
		this.typeId = leave.getLeaveType().getTypeId();
		this.reason = leave.getReason();
		this.days = leave.getDays();
		this.hours = leave.getHours();
		this.startTime = leave.getStartTime();
		this.file = leave.getFile();
	  }

}