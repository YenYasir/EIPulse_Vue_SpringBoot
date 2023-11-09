package com.eipulse.teamproject.service.employeeservice;
import com.eipulse.teamproject.dto.employeedto.PermissionMoveDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.employee.PermissionMove;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.employeerepository.PermissionInfoRepository;
import com.eipulse.teamproject.repository.employeerepository.PermissionMoveRepository;
import com.eipulse.teamproject.repository.employeerepository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return new PermissionMoveDTO(move);
    }

    // find all
    public List<PermissionMoveDTO> findAll(){
        List<PermissionMove> move = moveRepo.findAll();
        List<PermissionMoveDTO> dtos = new ArrayList<>();

        for (PermissionMove permissionMove : move){
            PermissionMoveDTO dto = new PermissionMoveDTO(permissionMove);
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
    //  select all 分頁功能
    public Page<PermissionMoveDTO> findByPage (Integer pageNumber){
        Pageable pgb = PageRequest.of(pageNumber-1, 5, Sort.Direction.ASC, "id");
        Page<PermissionMove> page =moveRepo.findAll(pgb);
        Page<PermissionMoveDTO> result = page.map(permissionMove -> new PermissionMoveDTO(permissionMove));
        return result;
    }
}
