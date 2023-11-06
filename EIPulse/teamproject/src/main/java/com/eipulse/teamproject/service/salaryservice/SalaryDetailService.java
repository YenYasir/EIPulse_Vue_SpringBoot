package com.eipulse.teamproject.service.salaryservice;


import com.eipulse.teamproject.dto.salarydto.SalaryDetailDto;
import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import com.eipulse.teamproject.repository.salaryrepository.SalaryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryDetailService {

    private SalaryDetailRepository detailRepo;

    @Autowired
    public SalaryDetailService(SalaryDetailRepository detailRepo) {
        this.detailRepo = detailRepo;
    }


    // 透過明細單號找Ok
    public SalaryDetailDto findById(Integer id) {
        SalaryDetail salaryDetail = detailRepo.findById(id).get();
        return new SalaryDetailDto(salaryDetail);
    }

}
