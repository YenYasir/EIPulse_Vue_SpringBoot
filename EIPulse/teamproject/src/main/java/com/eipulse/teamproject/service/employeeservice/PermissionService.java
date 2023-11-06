package com.eipulse.teamproject.service.employeeservice;
import com.eipulse.teamproject.entity.employee.Permission;
import com.eipulse.teamproject.repository.employeerepository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    private PermissionRepository permissionRepository;
    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }
    // 增加
    public void addPermission(Permission permission){
        permissionRepository.save(permission);
    }

    // 查詢單筆
    public Permission findById(Integer id){
        Optional<Permission> optional = permissionRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    // 刪除
    public void deletePermission(Integer id) {
        permissionRepository.deleteById(id);
    }

    // 查詢全部
    public List<Permission> findAllPermission(){
        return permissionRepository.findAll();
    }

    // 更新
    public Permission updatePermission(Integer permissionId,String permissionName,String permissionStatement){
       Permission oldData = permissionRepository.findById(permissionId)
               .orElseThrow(()-> new RuntimeException("Permission with ID"+ permissionId + " not found."));

       oldData.setPermissionName(permissionName);
       oldData.setPermissionStatement(permissionStatement);
       return permissionRepository.save(oldData);
    }
}
