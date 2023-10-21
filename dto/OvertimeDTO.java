package com.eipulse.teamproject.dto;

import java.sql.Time;
import com.eipulse.teamproject.entitys.Overtime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OvertimeDTO {

	private Integer formId;
	private Integer typeId;
	private String reason;
	private java.sql.Date date;
	private Time startTime;
	private Time endTime;
	private byte[] file;
	
	public OvertimeDTO(Overtime overtime) {
		this.formId = overtime.getFormId();
		this.typeId = overtime.getType().getId();
		this.reason = overtime.getReason();
		this.date = overtime.getDate();
		this.startTime = overtime.getStartTime();
		this.endTime = overtime.getEndTime();
		this.file = overtime.getFile();
	}	
	
}
