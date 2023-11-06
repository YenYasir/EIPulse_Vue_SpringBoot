package com.eipulse.teamproject.entity.clocktimeentity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "office_regions")
public class OfficeRegions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regions_id")
    private Integer regionsId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "regions_name")
    private String regionsName;
}
