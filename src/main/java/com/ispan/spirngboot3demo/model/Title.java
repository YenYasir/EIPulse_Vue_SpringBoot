package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "title", schema = "new_eipulse")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Integer id;

    @Column(name = "title_name", length = 50)
    private String titleName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;


    @OneToMany(mappedBy = "title")
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
}