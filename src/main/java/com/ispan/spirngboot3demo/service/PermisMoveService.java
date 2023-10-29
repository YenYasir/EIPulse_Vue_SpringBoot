package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.*;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.repository.PermissionInfoRepository;
import com.ispan.spirngboot3demo.repository.PermissionMoveRepository;
import com.ispan.spirngboot3demo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermisMoveService {

    private PermissionMoveRepository moveRepo;
    private EmployeeRepository empRepo;
    private PermissionInfoRepository infoRepo;
    private PermissionRepository permissionRepository;
    @Autowired
    public PermisMoveService(PermissionMoveRepository moveRepo, EmployeeRepository empRepo, PermissionInfoRepository infoRepo, PermissionRepository permissionRepository) {
        this.moveRepo = moveRepo;
        this.empRepo = empRepo;
        this.infoRepo = infoRepo;
        this.permissionRepository = permissionRepository;
    }

    // 增加
    public void  addPermisMove(PermissionMoveDTO dto){
        Employee emp = empRepo.findById(dto.getEmpId()).get();
        moveRepo.save(new PermissionMove(emp,dto.getBeforePermissionName(),dto.getAfterPermissionName(),dto.getReason(),dto.getEffectDate(),dto.getApprover()));
    }

    // find once (資料表編號、員編、修改前權限名稱、修改後權限名稱、原因、生效日、簽核人、編輯日期)
    public PermissionMoveDTO findById(Integer id){
        PermissionMove move = moveRepo.findById(id).orElseThrow(()->new RuntimeException("查詢錯誤"));
        return new PermissionMoveDTO(move.getId(),move.getEmp().getEmpId(),move.getBeforePermissionName(),move.getAfterPermissionName(),
                move.getReason(),move.getEffectDate(),move.getApprover(),move.getEditDate());
    }

    // find all
    public List<PermissionMoveDTO> findAll(){
        List<PermissionMove> move = moveRepo.findAll();
        List<PermissionMoveDTO> dtos = new ArrayList<>();

        for (PermissionMove permissionMove : move){
            PermissionMoveDTO dto = new PermissionMoveDTO(
                    permissionMove.getId(),
                    permissionMove.getEmp().getEmpId(),
                    permissionMove.getBeforePermissionName(),
                    permissionMove.getAfterPermissionName(),
                    permissionMove.getReason(),
                    permissionMove.getEffectDate(),
                    permissionMove.getApprover(),
                    permissionMove.getEditDate()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // delete
    public void delete(Integer id){
        moveRepo.deleteById(id);
    }

    // update
    public PermissionMoveDTO update(PermissionMoveDTO moveDTO){
        PermissionMove move = moveRepo.findById(moveDTO.getId()).orElseThrow(()->new RuntimeException("false"));
        System.out.println(moveDTO);

        // 只能更改原因 & 新增審核人
        move.setReason(moveDTO.getReason());
        move.setApprover(moveDTO.getApprover());
        PermissionMove result = moveRepo.save(move);
        // 這段是從資料庫把資料撈回來顯示
//        moveDTO.setReason(result.getReason());
//        moveDTO.setApprover(result.getApprover());
        return moveDTO;
    }
}
