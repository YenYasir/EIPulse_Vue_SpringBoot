package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission", schema = "new_eipulse")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "permissionId")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", nullable = false)
    private Integer permissionId;

    @Column(name = "permission_name", nullable = false, length = 50)
    private String permissionName;

    @Column(name = "permission_statement", nullable = false)
    private String permissionStatement;

    public Permission() {
    }

    public Permission(String permissionName, String permissionStatement) {
        this.permissionName = permissionName;
        this.permissionStatement = permissionStatement;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", permissionStatement='" + permissionStatement + '\'' +
                '}';
    }
}