package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.model.TitleMove;
import com.ispan.spirngboot3demo.model.TitleMoveDTO;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.repository.TitleMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class TitleMoveService {

    private TitleMoveRepository moveRepo;
    private EmployeeRepository empRepo;
    @Autowired

    public TitleMoveService(TitleMoveRepository moveRepo, EmployeeRepository empRepo) {
        this.moveRepo = moveRepo;
        this.empRepo = empRepo;
    }

    // add
    public void add(@RequestBody TitleMoveDTO dto){
        // 先查到 EmpID
        Employee emp = empRepo.findById(dto.getEmpId()).orElseThrow(()-> new RuntimeException("查詢無此資料"));
        // 將 DTO 的物件 save 到 TitleMove
        moveRepo.save(new TitleMove(emp,dto.getBeforeDeptInfo(),dto.getAfterDeptInfo(),
                dto.getReason(),dto.getEffectDate(),dto.getApprover()));
    }

    // find
    public TitleMoveDTO findById(Integer id){
        // 利用 ID 查詢 TitleMove 是否有資料
        TitleMove move = moveRepo.findById(id).orElseThrow(()->new RuntimeException("查無此資料"));
        // 使用 DTO 物件接收回傳的值
        return new TitleMoveDTO(move.getId(),move.getEmp().getEmpId(),move.getBeforeDeptInfo(),
                move.getAfterDeptInfo(),move.getReason(),move.getEffectDate(),move.getApprover(),move.getEditDate());
    }

    // find all
    public List<TitleMoveDTO> findAll(){
        List<TitleMove> move = moveRepo.findAll();
        List<TitleMoveDTO> dtos = new ArrayList<>();

        for (TitleMove titleMove : move){
            TitleMoveDTO dto = new TitleMoveDTO(
                    titleMove.getId(),
                    titleMove.getEmp().getEmpId(),
                    titleMove.getBeforeDeptInfo(),
                    titleMove.getAfterDeptInfo(),
                    titleMove.getReason(),
                    titleMove.getEffectDate(),
                    titleMove.getApprover(),
                    titleMove.getEditDate()
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
    public TitleMoveDTO update(TitleMoveDTO dto){
        TitleMove move = moveRepo.findById(dto.getId()).orElseThrow(()->new RuntimeException("查無此資料"));

        // 只能更改原因 & 新增審核人
        move.setReason(dto.getReason());
        move.setApprover(dto.getApprover());
        moveRepo.save(move);
        return  dto;
    }
}
