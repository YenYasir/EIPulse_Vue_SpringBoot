package com.eipulse.teamproject.entitys;

import java.sql.Timestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 這是表單總管，所有表單申請都從此處開始操作

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "form_record")
public class FormRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="form_id")
	private Integer formId;
	
	@OneToOne(mappedBy = "formRecord", cascade = CascadeType.ALL)
	private Leave leave;
	
	@OneToOne(mappedBy = "formRecord", cascade = CascadeType.ALL)
	private Overtime overtime;
	
	// 這是申請的表單類型(請假、加班.....)
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="type",nullable = false)
	private FormType type;
	
	// 這是表單狀態碼(未審核、批准、撤回....)
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="status",nullable = false)
	private StatusCode status;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="emp_id", referencedColumnName="emp_id")
	private Employee empId;
	
	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="update_time") 
	private Timestamp updateTime;
	
}
