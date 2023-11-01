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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

		Employee emp = empRepo.findById(id).orElseThrow(()->new RuntimeException("查無此資料"));

		return new EmpDTO(emp.getEmpId(),emp.getEmpName(),emp.getBirth(),emp.getEmail(), emp.getIdNumber(), emp.getGender(),
				emp.getPhone(), emp.getTel(), emp.getPhotoUrl(), emp.getAddress(), emp.getTitle().getTitleName(),
				emp.getEmpState(),emp.getHireDate(),emp.getLeaveDate(),emp.getEditDate());
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

	// findByNameLikeSearch
	public List<EmpDTO> findByNameLikeSearch(String name){
		List<EmpDTO> list = empRepo.findByNameLikeSearch(name);
		return list;
	}

	// update
	public EmpDTO updateEmp(EmpDTO empDTO) {
		Employee employee = empRepo.findById(empDTO.getEmpId())
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + empDTO.getEmpId()));

		// 更新employee對象的屬性
		employee.setEmpName(empDTO.getEmpName());
		employee.setPhone(empDTO.getPhone());
		employee.setEmail(empDTO.getEmail());
		employee.setAddress(empDTO.getAddress());
		employee.setTel(empDTO.getTel());

		// 保存並返回更新後的employee
		return new EmpDTO(empRepo.save(employee));
	}

	// 分頁功能
	public Page<EmpDTO> findByPage (Integer pageNumber){
		Pageable pgb = PageRequest.of(pageNumber-1, 5, Sort.Direction.DESC, "empId");
		Page<Employee> page = empRepo.findByEmpIdPage(pageNumber, pgb);
		Page<EmpDTO> result = page.map(employee -> new EmpDTO(employee));
		return result;
	}

	// 模糊收尋分頁功能
	public Page<EmpDTO> findByNamePage (Integer pageNumber,String name){
		Pageable pgb = PageRequest.of(pageNumber-1, 5, Sort.Direction.DESC, "empId");
		Page<Employee> page = empRepo.findByNamePage(name,pgb);
		Page<EmpDTO> result = page.map(employee -> new EmpDTO(employee));
		return result;
	}

}