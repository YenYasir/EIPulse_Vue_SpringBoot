package com.eipulse.teamproject.entitys.shopping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gift_cash_list")
public class GiftCashList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_cash_list_id", nullable = false)
    private Integer id;

    @Column(name = "cash", nullable = false)
    private Integer cash;

    @Column(name = "gift_type", nullable = false, length = 20)
    private String giftType;

}