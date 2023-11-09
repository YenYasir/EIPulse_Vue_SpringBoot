package com.eipulse.teamproject.entity.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.LinkedHashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dept", schema = "new_eipulse")
public class Dept {

    public Dept(String deptName, String deptOffice) {
        this.deptName = deptName;
        this.deptOffice = deptOffice;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id" )
    private Integer deptId;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "dept_office")
    private String deptOffice;

    @JsonManagedReference(value = "titles-dept")
    @OneToMany(mappedBy = "dept")
    private Set<Title> titles = new LinkedHashSet<>();

}
