package com.ispan.spirngboot3demo.service;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.DeptDTO;
import com.ispan.spirngboot3demo.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addDept(DeptDTO deptDTO){
        deptRepo.save(new Dept(deptDTO.getDeptName(), deptDTO.getDeptOffice()));
    }

    public Dept findById(Integer id){
        Optional<Dept> optional = deptRepo.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void deleteDept(Integer id) {
        deptRepo.deleteById(id);
    }

    public List<Dept> findAllDept(){
        return deptRepo.findAll();
    }

    public Dept update(Integer id,Dept newDept,Dept newOffice){
        Optional<Dept> optional = deptRepo.findById(id);
        if (optional!=null){
            Dept oldDept = optional.get();
            if (oldDept != newDept){
                deptRepo.save(newDept);
                deptRepo.save(newOffice);
            }
        }
            return null;
    }
}
