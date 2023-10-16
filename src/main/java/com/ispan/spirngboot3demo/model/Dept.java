package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dept", schema = "new_eipulse")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "deptId")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id" )
    private Integer deptId;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "dept_office")
    private String deptOffice;

    @OneToMany(mappedBy = "dept")
    private Set<Title> titles = new LinkedHashSet<>();

}