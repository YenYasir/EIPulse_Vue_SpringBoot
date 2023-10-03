package com.eipulse.teamproject.entitys;

<<<<<<< HEAD
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "title")
public class Title {
	@Id
	@Column(name = "title_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int titleId;
	@Column(name = "title_name")
	private String titleName;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "title", cascade = CascadeType.ALL)
	private List<EmployeeInfo> employeeInfo;
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "Title")
public class Title {
	@Id
    @Column(name="TitleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int titleId;
    @Column(name="TitleName")
    private String titleName;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "title", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmployeeInfo> employeeInfo;
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01

}
