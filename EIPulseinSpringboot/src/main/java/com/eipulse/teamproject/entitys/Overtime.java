package com.eipulse.teamproject.entitys;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="overtime")
public class Overtime {
	
	@Id
    @Column(name="form_id")
	private Integer formId;

	@Column(name = "reason")
	private String overtimeReason;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 固定時間在 Java 環境中的格式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 固定時間在 Java 環境中的格式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;

//	@Column(name = "overtime_total")
//	private void overtime() {
//	    Instant startInstant = startTime.toInstant();
//	    Instant endInstant = endTime.toInstant();
//
//	    Duration duration = Duration.between(startInstant, endInstant);
//
//	    long hours = duration.toHours() % 24; // 取得小時
//	    long minutes = duration.toMinutes() % 60; // 取得分鐘
//	}
	
}
