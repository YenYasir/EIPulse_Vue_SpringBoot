package com.ispan.spirngboot3demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		System.out.println(empDTO);
		empRepo.save(new Employee(empDTO.getEmpName(),empDTO.getBirth(),empDTO.getEmail(),empDTO.getIdNumber(),empDTO.getGender(),empDTO.getPhone(),empDTO.getTel(),empDTO.getPhotoUrl(),empDTO.getAddress(),title,empDTO.getHireDate(),empDTO.getEmpState()));
	}
	// find once
	public EmpDTO findById(Integer id) {
		Optional<Employee> optional = empRepo.findById(id);
	
		if(optional.isPresent()) {

			return new EmpDTO(optional.get());
			}
		return null;
	}

	// delete
	public void deleteEmp(Integer id) {
		empRepo.deleteById(id);
	}

	// find all ，避免將關聯資料都被撈出來，所以先資料放進去DTO裡，再設定建構子 get data
	public List<EmpDTO> findAllEmp(){
		List<Employee>employees = empRepo.findAll();
		List<EmpDTO>result = new ArrayList<>();
		for (Employee emp:employees) {
			EmpDTO dto = new EmpDTO(
					emp.getEmpId(),
					emp.getEmpName(),
					emp.getBirth(),
					emp.getEmail(),
					emp.getIdNumber(),
					emp.getGender(),
					emp.getPhone(),
					emp.getTel(),
					emp.getPhotoUrl(),
					emp.getAddress(),
					emp.getTitle().getTitleName(),
					emp.getEmpState(),
					emp.getHireDate(),
					emp.getLeaveDate(),
					emp.getEditDate()
			);
			result.add(dto);
		}
		return result;
	}

	// update
	public Employee updateEmp(EmpDTO empDTO) {
		Employee employee = empRepo.findById(empDTO.getEmpId())
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + empDTO.getEmpId()));

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
