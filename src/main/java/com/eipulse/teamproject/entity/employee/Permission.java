package com.eipulse.teamproject.entity.employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission", schema = "new_eipulse")
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
}