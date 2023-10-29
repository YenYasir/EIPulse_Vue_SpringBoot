package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.*;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.repository.PermissionInfoRepository;
import com.ispan.spirngboot3demo.repository.PermissionMoveRepository;
import com.ispan.spirngboot3demo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermisInfoService {

    private EmployeeRepository employeeRepository;
    private PermissionRepository permissionRepository;
    private PermissionInfoRepository permissionInfoRepository;
    private PermissionMoveRepository moveRepo;
    @Autowired
    public PermisInfoService(EmployeeRepository employeeRepository, PermissionRepository permissionRepository, PermissionInfoRepository permissionInfoRepository, PermissionMoveRepository moveRepo) {
        this.employeeRepository = employeeRepository;
        this.permissionRepository = permissionRepository;
        this.permissionInfoRepository = permissionInfoRepository;
        this.moveRepo = moveRepo;
    }

    // add
    public void addInfo(PermissionInfoDTO permissionInfoDTO){
        //
        Employee emp = employeeRepository.findById(permissionInfoDTO.getEmpId()).get();
        Permission permis = permissionRepository.findById(permissionInfoDTO.getPermissionId()).get();

        permissionInfoRepository.save(new PermissionInfo(emp,permis));
    }

    // find once
    public PermissionInfoDTO findById(Integer id){
        PermissionInfo permissionInfo  = permissionInfoRepository.findById(id).orElseThrow(()->new RuntimeException("查詢錯誤"));

        // 列出員工編號、權限編號、權限名稱
        return new PermissionInfoDTO(permissionInfo.getId(), permissionInfo.getEmp().getEmpId(),permissionInfo.getEmp().getEmpName(),permissionInfo.getPermission().getPermissionId(),permissionInfo.getPermission().getPermissionName());
    }
    // find all
    public List<PermissionInfoDTO> findAll(){

        // find all ，避免將關聯資料都被撈出來，所以先資料放進去DTO裡，再設定建構子 get data
        List<PermissionInfo> permissionInfos = permissionInfoRepository.findAll();
        List<PermissionInfoDTO> permissionInfoDTOs = new ArrayList<>();

        for (PermissionInfo permissionInfo : permissionInfos) {
            PermissionInfoDTO dto = new PermissionInfoDTO(
                    permissionInfo.getId(),
                    permissionInfo.getEmp().getEmpId(),
                    permissionInfo.getEmp().getEmpName(),
                    permissionInfo.getPermission().getPermissionId(),
                    permissionInfo.getPermission().getPermissionName()
            );
            permissionInfoDTOs.add(dto);
        }
        System.out.println(permissionInfoDTOs);
        return permissionInfoDTOs;
    }


    // delete
    public void deleteInfo(Integer id){
        permissionInfoRepository.deleteById(id);
    }

    // update info 的資料就會自動新增權限異動表的紀錄
    // 建立一個共用 DTO(permissionInfoDTO) 再各自把值分出來處理
    @Transactional
    public PermissionInfo updateAndLogChange(PermissionInfoDTO permissionInfoDTO) {
        // 查找和驗證員工信息
        Employee emp = employeeRepository.findById(permissionInfoDTO.getEmpId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        // 查找和驗證舊 Permission 資料
        PermissionInfo originalInfo = permissionInfoRepository.findById(permissionInfoDTO.getEmpId())
                .orElseThrow(() -> new RuntimeException("PermissionInfo not found"));
        Permission originalPermission = originalInfo.getPermission();


        // 更新 PermissionInfo
        Permission newPermission = permissionRepository.findById(permissionInfoDTO.getPermissionId())
                .orElseThrow(() -> new RuntimeException("Invalid permission ID"));
        originalInfo.setPermission(newPermission);


        PermissionInfo updatedInfo = permissionInfoRepository.save(originalInfo);

        // 驗證新的 Permission 是否存在
        if (newPermission == null || originalPermission == null) {
            throw new RuntimeException("Invalid permission names");
        }
        try {
        // 增加 permissionMove 異動紀錄
        moveRepo.save(new PermissionMove(emp, originalPermission.getPermissionName(), newPermission.getPermissionName(),
                permissionInfoDTO.getReason(),permissionInfoDTO.getEffectDate(), permissionInfoDTO.getApprover()));
            System.out.println("OK");
        }catch (Exception e){
           e.printStackTrace();
        }

        return updatedInfo;
    }
}
