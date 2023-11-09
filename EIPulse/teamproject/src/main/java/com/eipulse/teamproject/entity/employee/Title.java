package com.eipulse.teamproject.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "title", schema = "new_eipulse")
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Integer id;

    @Column(name = "title_name", length = 50)
    private String titleName;


    @JsonBackReference(value = "titles-dept")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;


    @JsonManagedReference(value = "emp-title")
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "title")
    private Set<Employee> employees = new LinkedHashSet<>();

    public Title() {
    }

    public Title(String titleName, Dept dept) {
        this.titleName = titleName;
        this.dept = dept;
    }

    public Title(Integer id, String titleName, Dept dept) {
        this.id = id;
        this.titleName = titleName;
        this.dept = dept;
    }

    public Title(Integer id,String titleName) {
        this.id = id;
        this.titleName = titleName;
    }
}