package com.eipulse.teamproject.entitys;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="dayoff_status")
public class LeaveStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "status")//註記是否通過申請
	private String status;
	
	@Column(name = "reason")//如果是拒絕，寫出拒絕原因
	private String reason;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") //提出申請的時間
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_time")
	private Date applyTime;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") //申請通過的時間
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "final_time")
	private Date finalTime;
	
	@Column(name = "supervusor")
	private String supervisor; //審核人
	
}
