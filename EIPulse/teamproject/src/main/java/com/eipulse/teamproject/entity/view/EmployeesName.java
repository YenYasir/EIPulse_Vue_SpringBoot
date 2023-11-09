package com.eipulse.teamproject.entity.view;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "employees_name")
public class EmployeesName {

    @Id
    @NotNull
    @Column(name = "emp_id", nullable = false)
    private Integer empId;

    @Size(max = 50)
    @NotNull
    @Column(name = "emp_name", nullable = false, length = 50)
    private String empName;

}