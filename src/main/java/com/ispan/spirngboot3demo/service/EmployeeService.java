package com.ispan.spirngboot3demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.EmpDTO;
import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.model.Title;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {


	private TitleRepository titleRepository;
	private EmployeeRepository empRepo;
	@Autowired
	public EmployeeService(EmployeeRepository empRepo,TitleRepository titleRepository) {
		this.empRepo = empRepo;
		this.titleRepository= titleRepository;
	}

	// add
	public void addEmp(EmpDTO empDTO) {
		Title title = titleRepository.findById(empDTO.getTitleId()).get();
		empRepo.save(new Employee(empDTO.getEmpName(),empDTO.getBirth(),empDTO.getEmail(),empDTO.getIdNumber(),empDTO.getGender(),empDTO.getPhone(),empDTO.getTel(),empDTO.getPhotoUrl(),empDTO.getAddress(),title,empDTO.getEmpState()));
	}
	// find once
	public Employee findById(Integer id) {
		Optional<Employee> optional = empRepo.findById(id);
	
		if(optional.isPresent()) {

			return optional.get();
			}
		return null;
	}

	// delete
	public void deleteEmp(Integer id) {
		empRepo.deleteById(id);
	}

	// find all
	public List<Employee> findAllEmp(){
		return empRepo.findAll();
		
	}

	// update
	public Employee updateEmp(EmpDTO empDTO) {
		Employee employee = empRepo.findById(empDTO.getId())
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + empDTO.getId()));

		// 更新employee對象的屬性
		employee.setEmpName(empDTO.getEmpName());
		employee.setPhone(empDTO.getPhone());
		employee.setEmail(empDTO.getEmail());
		employee.setAddress(empDTO.getAddress());
		employee.setTel(empDTO.getTel());

		// 保存並返回更新後的employee
		return empRepo.save(employee);
	}

}
