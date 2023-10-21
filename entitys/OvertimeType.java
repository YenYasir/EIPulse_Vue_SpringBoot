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

// 加班種類(平日加班、假日加班、國定假日加班)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="overtime_type")
public class OvertimeType {
	
	@Id
    @Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="type")
	private String type;
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},mappedBy="type")
	@JsonIgnore
	private List<Overtime> overtime;
	
	

}
