package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.DeptDTO;
import com.ispan.spirngboot3demo.model.EmpDTO;
import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.repository.DeptRepository;
import org.hibernate.dialect.PgJdbcHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeptService {

    private DeptRepository deptRepo;
    @Autowired
    public DeptService(DeptRepository deptRepo) {
        this.deptRepo = deptRepo;
    }

    // 增加部門(部門名稱、辦公室)
    public void addDept(DeptDTO deptDTO){
        deptRepo.save(new Dept(deptDTO.getDeptName(), deptDTO.getDeptOffice()));
    }

    // 查詢1筆
    public Dept findById(Integer id){
        Optional<Dept> optional = deptRepo.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    // 刪除
    public void deleteDept(Integer id) {
        deptRepo.deleteById(id);
    }
    // 查詢全部
    public List<Dept> findAllDept(){
        return deptRepo.findAll();
    }

    // 更新
    public DeptDTO update(DeptDTO dto){
        Dept dept = deptRepo.findById(dto.getDeptId())
                .orElseThrow(()-> new RuntimeException("ID not found"));
        // 更新部門名稱及辦公室
        dept.setDeptName(dto.getDeptName());
        dept.setDeptOffice(dto.getDeptOffice());

        // save data and return result
        return new DeptDTO(deptRepo.save(dept));
    }
    // 分頁功能 by name
    public Page<DeptDTO> findByNamePage (Integer pageNumber,String name){
        Pageable pgb = PageRequest.of(pageNumber-1, 5, Sort.Direction.DESC, "deptId");
        Page<Dept> page = deptRepo.findByDeptNamePage(name,pgb);
        Page<DeptDTO> result = page.map(dept -> new DeptDTO(dept));
        return result;
    }

    //  select all 分頁功能
    public Page<DeptDTO> findByPage (Integer pageNumber){
        Pageable pgb = PageRequest.of(pageNumber-1, 5, Sort.Direction.DESC, "deptId");
        Page<Dept> page = deptRepo.findByPage(pageNumber,pgb);
        Page<DeptDTO> result = page.map(dept -> new DeptDTO(dept));
        return result;
    }
}
