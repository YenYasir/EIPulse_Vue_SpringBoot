package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission_info", schema = "new_eipulse")
public class PermissionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee emp;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;



    public PermissionInfo() {
    }

    public PermissionInfo(Employee emp, Permission permission) {
        this.emp = emp;
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "PermissionInfo{" +
                "id=" + id +
                ", emp=" + emp +
                ", permission=" + permission +
                '}';
    }
}