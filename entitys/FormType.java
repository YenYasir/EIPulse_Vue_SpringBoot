package com.eipulse.teamproject.entitys;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//這是表單申請種類，選擇要加班、請假、還是其他功能

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "form_type")
public class FormType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer typeId;
	
	@Column(name="type_name")
	private String typeName;
	
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},mappedBy="type")
	private List<FormRecord> formRecord;

}
