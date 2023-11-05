package com.eipulse.teamproject.dto.salarydto;

import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import com.eipulse.teamproject.entity.salaryentity.SalaryMonthRecord;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
public class SalaryTrialDto {

    private SalaryMonthRecordDto salaryMonthRecordDto;

    private List<DetailDto> detaildDto;


    public SalaryTrialDto(SalaryMonthRecordDto recordDto, List<DetailDto> dDto) {
        this.salaryMonthRecordDto = recordDto;

        List<DetailDto> dDtoList = new ArrayList<>();
        for (DetailDto detailDto : dDto) {
            dDtoList.add(detailDto);
        }
        this.detaildDto = dDtoList;
    }

    public SalaryTrialDto(SalaryMonthRecord salaryRecord, List<SalaryDetail> details) {
        this.salaryMonthRecordDto = new SalaryMonthRecordDto(salaryRecord);

        // 賦值
        List<DetailDto> detailDtos = new ArrayList<>();
        for (SalaryDetail detail : details) {
            DetailDto detailDto = new DetailDto(detail);
            detailDtos.add(detailDto);
        }

        this.detaildDto = detailDtos;
    }

    public SalaryTrialDto() {
    }

}
