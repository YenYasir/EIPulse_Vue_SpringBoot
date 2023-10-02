package com.eipulse.teamproject.entitys;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
@Data
@Entity
@Table(name = "leave")
public class Leave {
	
	@Id
    @Column(name="form_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer formId;

	@Column(name = "reason")
	private String leaveReason;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") // 固定時間在 Java 環境中的格式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") // 固定時間在 Java 環境中的格式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="type_id")
	private LeaveType leaveType;
    
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "dayOff")
	private Set<LeaveFile> leaveFile=new HashSet<>();
	
//	@Column(name = "dayoff_total")
//	private void offDays() {
//	    Instant startInstant = startTime.toInstant();
//	    Instant endInstant = endTime.toInstant();
//
//	    Duration duration = Duration.between(startInstant, endInstant);
//
//	    long days = duration.toDays(); // 取得天數
//	    long hours = duration.toHours() % 24; // 取得小時
//	    long minutes = duration.toMinutes() % 60; // 取得分鐘
//	}
	
	@Formula("DATEDIFF('MINUTE', start_time, end_time)")
	@Column(name = "dayoff_total")
	private Long leaveDays;
}
