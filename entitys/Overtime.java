package com.eipulse.teamproject.entitys;


import java.sql.Time;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 加班申請單

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="apply_overtime")
public class Overtime {
	
	@Id
	@Column(name="form_id")
	private Integer formId;
	
    @OneToOne
    @JoinColumn(name = "form_id",insertable = false,updatable = false) 
    private FormRecord formRecord;
	
    // 加班種類(平日加班、假日加班、國定假日加班)
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="type",nullable = false)
	private OvertimeType type;

	@Column(name = "reason")
	private String reason;
	
	@Column(name="date")
	private java.sql.Date date;
	
	@Column(name = "start_time")
	private Time startTime;

	@Column(name = "end_time")  
	private Time endTime;
	
	@Lob
	@Column(name="file")
	private byte[] file;
	
}
