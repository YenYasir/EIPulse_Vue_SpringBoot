package com.eipulse.teamproject.entity.formapproval;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "Holiday")
public class Holiday {

    @Id
    @Column(name = "holiday_id")
    private int holidayId;
    @Column(name = "holiday_date")
    private LocalDateTime holidayDate;
    @Column(name = "holiday_name")
    private String holidayName;
}
