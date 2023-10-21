package com.eipulse.teamproject.entitys;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

// 請假申請單

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apply_leave")
public class Leave {
	
	
	@Id
	@Column(name="form_id")
	private Integer formId;
	
	@JsonIgnore
    @OneToOne
    @JoinColumn(name = "form_id",insertable = false,updatable = false) 
    private FormRecord formRecord;
	
//	@JsonIgnoreProperties({"days", "type", "status", "remark"}) // 忽略其他屬性，只保留 typeId
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "type",nullable = false)
    private LeaveType leaveType;
    
//    @Column(name="type")
//    private Integer typeId;
    
    @Column(name="reason")
    private String reason;
		
	@Column(name="days")
	private Integer days;
	
	@Column(name="hours")
	private Integer hours;
	

	@Column(name="start_time")
	private LocalDateTime startTime;
	
	@Lob
	@Column(name="file")
	private byte[] file;
	
}	
	

