package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.repository.PermissionInfoRepository;
import com.ispan.spirngboot3demo.repository.PermissionMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermisMoveService {

    private PermissionMoveRepository moveRepo;
    private EmployeeRepository empRepo;
    private PermissionInfoRepository infoRepo;
    @Autowired
    public PermisMoveService(PermissionMoveRepository moveRepo, EmployeeRepository empRepo, PermissionInfoRepository infoRepo) {
        this.moveRepo = moveRepo;
        this.empRepo = empRepo;
        this.infoRepo = infoRepo;
    }

    // 增加
    public void  addPermisMove(){

    }
}
