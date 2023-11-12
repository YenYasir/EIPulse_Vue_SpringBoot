package com.eipulse.teamproject.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "alldept_pepole")
public class AlldeptPepole {

    @NotNull
    @Column(name = "dept_id", nullable = false)
    private Integer deptId;

    @Size(max = 50)
    @NotNull
    @Column(name = "dept_name", nullable = false, length = 50)
    private String deptName;

    @Size(max = 50)
    @NotNull
    @Column(name = "title_name", nullable = false, length = 50)
    private String titleName;

    @Id
    @Size(max = 50)
    @NotNull
    @Column(name = "emp_id", nullable = false, length = 50)
    private Integer empId;

    @Size(max = 50)
    @NotNull
    @Column(name = "emp_name", nullable = false, length = 50)
    private String empName;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    private String email;
}
