package com.eipulse.teamproject.service.employeeservice;
import com.eipulse.teamproject.dto.employeedto.ResignDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.employee.ResignRecord;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.employeerepository.ResignRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResignRecordService {

    private EmployeeRepository empRepo;
    private ResignRecordRepository resignRepo;

    @Autowired
    public ResignRecordService(EmployeeRepository empRepo, ResignRecordRepository resignRepo) {
        this.empRepo = empRepo;
        this.resignRepo = resignRepo;
    }

    // 新增
    public void addResign(ResignDTO resignDTO){
        Employee emp = empRepo.findById(resignDTO.getEmpId()).orElseThrow(()->new RuntimeException("Employee not found"));


        resignRepo.save(new ResignRecord(emp,resignDTO.getReason(),resignDTO.getLeaveDate(),
                resignDTO.isQuit(),resignDTO.isTransferForm()));
    }

    // 查詢單筆 (員編、原因、離職日、簽核人、離職證明書、勞健保轉出單)
    public ResignDTO findById(Integer id){
        ResignRecord resign = resignRepo.findById(id).orElseThrow(()-> new RuntimeException("查詢錯誤"));
        return  new ResignDTO(resign);
    }
    // 刪除
    public void deleteResign(Integer id) {
        resignRepo.deleteById(id);
    }

    // 查詢全部
    public List<ResignDTO> findAllResign(){
        List<ResignRecord> resigns = resignRepo.findAll();
        List<ResignDTO> dto =  new ArrayList<>();

        for (ResignRecord resignRecord : resigns) {
            ResignDTO dtos = new ResignDTO(resignRecord);
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
