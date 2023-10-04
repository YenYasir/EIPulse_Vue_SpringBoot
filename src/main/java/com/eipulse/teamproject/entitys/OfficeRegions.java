package com.eipulse.teamproject.entitys;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "office_regions")
public class OfficeRegions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "regions_id")
	private Integer regionsId;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "regions_name")
	private String regionsName;

//    mappedBy確保映射關係，能透過OfficeRegions抓取到關聯的ClockTime資料
	@OneToMany(mappedBy = "officeRegions", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<ClockTime> clockTimes;

}
