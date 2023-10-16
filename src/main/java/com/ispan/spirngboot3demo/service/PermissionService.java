package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.Permission;
import com.ispan.spirngboot3demo.repository.DeptRepository;
import com.ispan.spirngboot3demo.repository.PermissionRepository;
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

    public void addPermission(Permission permission){
        permissionRepository.save(permission);
    }

    public Permission findById(Integer id){
        Optional<Permission> optional = permissionRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void deletePermission(Integer id) {
        permissionRepository.deleteById(id);
    }

    public List<Permission> findAllPermission(){
        return permissionRepository.findAll();
    }

    public Permission updatePermission(Integer permissionId,String permissionName,String permissionStatement){
       Permission oldData = permissionRepository.findById(permissionId)
               .orElseThrow(()-> new RuntimeException("Permission with ID"+ permissionId + " not found."));

       oldData.setPermissionName(permissionName);
       oldData.setPermissionStatement(permissionStatement);
       return permissionRepository.save(oldData);
    }
}
