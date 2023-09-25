package com.eipulse.teamproject.entitys;

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

}
