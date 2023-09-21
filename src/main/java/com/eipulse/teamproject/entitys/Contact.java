package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "contact")
public class Contact {

    @Column(name="Phone")
    private String phone;
    @Column(name="Tel")
    private String tel;
    @Column(name="Address")
    private String address;
    @Column(name="Email")
    private String email;
	@Column(name="LineId")
	private String lineId;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmpId", referencedColumnName = "empid")
	private Employee employee;


}
