package com.eipulse.teamproject.entity.salaryentity;


import java.util.List;

import com.eipulse.teamproject.dto.salarydto.SubjectTypeDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subject_type", schema = "finalproject")
public class SubjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "calculate_type")
    private String calculateType;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "amount_default")
    private Integer amountDefault;


    @Column(name = "status")
    private String status;


    @JsonManagedReference(value = "subject-type-detail")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subjectType", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SalaryDetail> details;

    public SubjectType() {
    }

    public SubjectType(Integer subjectId, String subjectName, String calculateType, String frequency,
                       Integer amountDefault, String status) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.calculateType = calculateType;
        this.frequency = frequency;
        this.amountDefault = amountDefault;
        this.status = status;
    }

    public SubjectType(SubjectTypeDto subjectDto) {
        this.subjectId = subjectDto.getSubjectId();
        this.subjectName = subjectDto.getSubjectName();
        this.calculateType = subjectDto.getCalculateType();
        this.frequency = subjectDto.getFrequency();
        this.status = subjectDto.getStatus();
        this.amountDefault = subjectDto.getAmountDefault();
    }


}