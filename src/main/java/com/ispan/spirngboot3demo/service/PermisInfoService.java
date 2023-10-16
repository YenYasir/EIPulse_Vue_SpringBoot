package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.model.Permission;
import com.ispan.spirngboot3demo.model.PermissionInfo;
import com.ispan.spirngboot3demo.model.PermissionInfoDTO;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.repository.PermissionInfoRepository;
import com.ispan.spirngboot3demo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermisInfoService {

    private EmployeeRepository employeeRepository;
    private PermissionRepository permissionRepository;
    private PermissionInfoRepository permissionInfoRepository;
    @Autowired
    public PermisInfoService(EmployeeRepository employeeRepository, PermissionRepository permissionRepository, PermissionInfoRepository permissionInfoRepository) {
        this.employeeRepository = employeeRepository;
        this.permissionRepository = permissionRepository;
        this.permissionInfoRepository = permissionInfoRepository;
    }


    // add
    public void addInfo(PermissionInfoDTO permissionInfoDTO){
        Employee emp = employeeRepository.findById(permissionInfoDTO.getEmpId()).get();
        Permission permis = permissionRepository.findById(permissionInfoDTO.getPermissionId()).get();

        permissionInfoRepository.save(new PermissionInfo(emp,permis));
    }

    // find once
    public PermissionInfoDTO findById(Integer id){
        PermissionInfo permissionInfo  = permissionInfoRepository.findById(id).orElseThrow(()->new RuntimeException("查詢錯誤"));

        return new PermissionInfoDTO(permissionInfo.getId(), permissionInfo.getEmp().getEmpId(),permissionInfo.getEmp().getEmpName(),permissionInfo.getPermission().getPermissionId(),permissionInfo.getPermission().getPermissionName());
    }
    // find all
    public List<PermissionInfoDTO> findAll(){
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

    // update
    public PermissionInfo update(PermissionInfoDTO permissionInfoDTO){
        PermissionInfo info = permissionInfoRepository.findById(permissionInfoDTO.getEmpId())
                .orElseThrow(()-> new RuntimeException(permissionInfoDTO.getId()+"can't find"));
        Permission permis = permissionRepository.findById(permissionInfoDTO.getPermissionId())
                .orElseThrow(() -> new RuntimeException("Unable to find Permission with ID: " + permissionInfoDTO.getPermissionId()));

        info.setPermission(permis);
        return permissionInfoRepository.save(info);
    }
}
