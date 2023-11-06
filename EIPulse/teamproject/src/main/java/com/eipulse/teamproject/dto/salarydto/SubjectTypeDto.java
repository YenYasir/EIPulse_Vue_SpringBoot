package com.eipulse.teamproject.dto.salarydto;

import com.eipulse.teamproject.entity.salaryentity.SubjectType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectTypeDto {


    private Integer subjectId;


    private String subjectName;


    private String calculateType;


    private String frequency;


    private Integer amountDefault;


    private String status;


    public SubjectTypeDto() {
    }

    public SubjectTypeDto(SubjectType subject) {
        this.subjectId = subject.getSubjectId();
        this.subjectName = subject.getSubjectName();
        this.calculateType = subject.getCalculateType();
        this.frequency = subject.getFrequency();
        this.amountDefault = subject.getAmountDefault();
        this.status = subject.getStatus();
    }


}
