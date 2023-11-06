package com.eipulse.teamproject.service.employeeservice;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.eipulse.teamproject.config.SecurityConfig;
import com.eipulse.teamproject.dto.employeedto.EmpDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.employee.PermissionInfo;
import com.eipulse.teamproject.entity.employee.Title;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.employeerepository.TitleRepository;

@Service
public class EmployeeService {

	private SecurityConfig securityConfig;
	private TitleRepository titleRepository;
	private JavaMailSender javaMailSender;
	private EmployeeRepository empRepo;

	@Autowired
	public EmployeeService(EmployeeRepository empRepo, TitleRepository titleRepository, JavaMailSender javaMailSender,
			SecurityConfig securityConfig) {
		this.empRepo = empRepo;
		this.titleRepository = titleRepository;
		this.javaMailSender = javaMailSender;
		this.securityConfig = securityConfig;
	}

	// add
	public void addEmp(EmpDTO empDTO) {
		Title title = titleRepository.findById(empDTO.getTitleId()).get();
		System.out.println(empDTO);
		empRepo.save(new Employee(empDTO.getEmpName(), empDTO.getBirth(), empDTO.getEmail(), empDTO.getIdNumber(),
				empDTO.getGender(), empDTO.getPhone(), empDTO.getTel(), empDTO.getPhotoUrl(), empDTO.getAddress(),
				title, empDTO.getHireDate(), empDTO.getEmpState()));
	}

	// find once
	public EmpDTO findById(Integer id) {
		Optional<Employee> optional = empRepo.findById(id);

		if (optional.isPresent()) {

			return new EmpDTO(optional.get());
		}
		return null;
	}

	// delete
	public void deleteEmp(Integer id) {
		empRepo.deleteById(id);
	}

	// find all ，避免將關聯資料都被撈出來，所以先資料放進去DTO裡，再設定建構子 get data
	public List<EmpDTO> findAllEmp() {
		List<Employee> employees = empRepo.findAll();
		List<EmpDTO> result = new ArrayList<>();
		for (Employee emp : employees) {
			EmpDTO dto = new EmpDTO(emp.getEmpId(), emp.getEmpName(), emp.getBirth(), emp.getEmail(), emp.getIdNumber(),
					emp.getGender(), emp.getPhone(), emp.getTel(), emp.getPhotoUrl(), emp.getAddress(),
					emp.getTitle().getTitleName(), emp.getEmpState(), emp.getHireDate(), emp.getLeaveDate(),
					emp.getEditDate(), emp.getPassword());
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

	// 員工登入
	public EmpDTO empLogin(EmpDTO empDTO) {
		Optional<Employee> optional = empRepo.findById(empDTO.getEmpId());

		// 初始化一個用於儲存權限ID的集合
		Set<Integer> permissionIds = new LinkedHashSet<>();

		optional.ifPresentOrElse(employee -> {
			// TODO: 使用更安全的方式比對密碼，例如bcrypt
			if (empDTO.getPassword() != null && employee.getPassword() != null
					&& empDTO.getPassword().equals(employee.getPassword())
					|| securityConfig.passwordEncoder().matches(empDTO.getPassword(), employee.getPassword())) {
				empDTO.setEmpName(employee.getEmpName());
				empDTO.setEmail(employee.getEmail());
				empDTO.setBirth(employee.getBirth());
				empDTO.setIdNumber(employee.getIdNumber());
				empDTO.setGender(employee.getGender());
				empDTO.setPhone(employee.getPhone());
				empDTO.setTel(employee.getTel());
				empDTO.setAddress(employee.getAddress());

				// 獲取員工的所有權限ID
				for (PermissionInfo permissionInfo : employee.getPermissionInfos()) {
					permissionIds.add(permissionInfo.getPermission().getPermissionId());
				}

				// 設置權限ID
				empDTO.setPermissionId(permissionIds);
			} else {
				// TODO: 抛出更具體的異常
				throw new SecurityException("密碼不匹配");
			}
		}, () -> {
			// TODO: 抛出更具體的異常
			throw new NoSuchElementException("用戶ID不存在");
		});

		return empDTO;
	}

	public List<EmpDTO> findSameDeptEmp(Integer empId) {
		Employee employee = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("error"));
		return empRepo.findSameDeptEmp(employee.getTitle().getDept().getDeptId());
	}

	public EmpDTO forgetPassword(EmpDTO empDTO, Integer otpVal) {
		Optional<Employee> optional = empRepo.findByEmail(empDTO.getEmail());
		if (optional.isPresent()) {
			Employee employee = optional.get();
//進階版Thread
			CompletableFuture.runAsync(() -> {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("anddie0904@gmail.com");
				message.setTo(employee.getEmail());
				message.setSubject("Eipulse員工驗證碼");
				message.setText("您的驗證碼：" + otpVal);
				javaMailSender.send(message);
			}).exceptionally(ex -> {
				throw new UnsupportedOperationException("mail發送失敗");
			});
			empDTO.setEmpId(employee.getEmpId());
			return empDTO;
		}
		throw new UnsupportedOperationException("無此email");
	}

	public boolean newPassword(EmpDTO empDTO) {
		Employee employee = empRepo.findById(empDTO.getEmpId()).orElseThrow(() -> new RuntimeException("error"));

		employee.setPassword(securityConfig.passwordEncoder().encode(empDTO.getNewPassword()));
		empRepo.save(employee);
		return true;
	}

}
