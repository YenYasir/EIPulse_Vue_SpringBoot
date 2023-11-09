package com.eipulse.teamproject.service.employeeservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eipulse.teamproject.dto.employeedto.DeptDTO;
import com.eipulse.teamproject.entity.employee.Dept;
import com.eipulse.teamproject.repository.employeerepository.DeptRepository;

@Service
public class DeptService {

	private DeptRepository deptRepo;

	@Autowired
	public DeptService(DeptRepository deptRepo) {
		this.deptRepo = deptRepo;
	}

	// 增加部門(部門名稱、辦公室)
	public void addDept(DeptDTO deptDTO) {
		deptRepo.save(new Dept(deptDTO.getDeptName(), deptDTO.getDeptOffice()));
	}

	// 查詢1筆
	public DeptDTO findById(Integer id){
		Dept dept = deptRepo.findById(id).orElseThrow(()->new RuntimeException("查無此資料"));
		return new DeptDTO(dept.getDeptId(), dept.getDeptName(), dept.getDeptOffice());
	}

	// 刪除
	public void deleteDept(Integer id) {
		deptRepo.deleteById(id);
	}

	// 查詢全部
	public List<DeptDTO> findAllDept() {
		return deptRepo.findAllDetp();
	}

	// 更新
	public DeptDTO update(DeptDTO dto) {
		Dept dept = deptRepo.findById(dto.getDeptId()).orElseThrow(() -> new RuntimeException("ID not found"));
		// 更新部門名稱及辦公室
		dept.setDeptName(dto.getDeptName());
		dept.setDeptOffice(dto.getDeptOffice());

		// save data and return result
		return new DeptDTO(deptRepo.save(dept));
	}

	// 分頁功能 by name
	public Page<DeptDTO> findByNamePage(Integer pageNumber, String name) {
		Pageable pgb = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "deptId");
		Page<Dept> page = deptRepo.findByDeptNamePage(name, pgb);
		Page<DeptDTO> result = page.map(dept -> new DeptDTO(dept));
		return result;
	}

	// select all 分頁功能
	public Page<DeptDTO> findByPage(Integer pageNumber) {
		Pageable pgb = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "deptId");
		Page<Dept> page =deptRepo.findAll(pgb);
		Page<DeptDTO> result = page.map(dept -> new DeptDTO(dept));
		return result;
	}
}
