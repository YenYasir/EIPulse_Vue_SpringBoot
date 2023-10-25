package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.*;
import com.ispan.spirngboot3demo.repository.DeptRepository;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.repository.ResignRecordRepository;
import com.ispan.spirngboot3demo.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResignRecordService {

    @Autowired
    private EmployeeRepository empRepo;
    @Autowired
    private ResignRecordRepository resignRepo;
    public ResignRecordService(EmployeeRepository empRepo, ResignRecordRepository resignRepo) {
        this.empRepo = empRepo;
        this.resignRepo = resignRepo;
    }

    // 新增
    public void addResign(ResignDTO resignDTO){
        Employee emp = empRepo.findById(resignDTO.getEmpId()).get();

        resignRepo.save(new ResignRecord(emp,resignDTO.getReason()));
    }

    // 查詢單筆
    public ResignDTO findById(Integer id){
       ResignRecord resign = resignRepo.findById(id).orElseThrow(()-> new RuntimeException("查詢錯誤"));
       return  new ResignDTO(resign.getId(), resign.getEmp().getEmpId(),resign.getReason(),resign.getLeaveDate(),resign.getApprover());
    }
    // 刪除
    public void deleteResign(Integer id) {
        resignRepo.deleteById(id);
    }

    // 查詢全部職位
    public List<ResignDTO> findAllResign(){
        List<ResignRecord> resigns = resignRepo.findAll();
        List<ResignDTO> dto =  new ArrayList<>();

        for (ResignRecord resignRecord : resigns) {
            ResignDTO dtos = new ResignDTO(
                    resignRecord.getId(),
                    resignRecord.getEmp().getEmpId(),
                    resignRecord.getReason(),
                    resignRecord.getLeaveDate(),
                    resignRecord.getApprover(),
                    resignRecord.getEditDate()
            );
            dto.add(dtos);
        }
        return dto;
    }


    // 更新修改離職
    public ResignDTO update(ResignDTO resignDTO){
        ResignRecord resign = resignRepo.findByEmpId(resignDTO.getEmpId());
        System.out.println(resignDTO);

        // 這段是把資料更新至資料庫
        resign.setApprover(resignDTO.getApprover());
        resign.setReason(resignDTO.getReason());
        ResignRecord record = resignRepo.save(resign);
        // 這段是從資料庫把資料撈回來顯示
        resignDTO.setReason(record.getReason());
        resignDTO.setApprover(record.getApprover());
        return resignDTO;

    }
}
