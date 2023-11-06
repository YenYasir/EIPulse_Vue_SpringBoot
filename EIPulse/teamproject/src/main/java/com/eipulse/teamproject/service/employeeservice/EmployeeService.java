package com.eipulse.teamproject.service.employeeservice;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.eipulse.teamproject.config.SecurityConfig;
import com.eipulse.teamproject.dto.employeedto.EmpDTO;
import com.eipulse.teamproject.dto.employeedto.TitleMoveDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.employee.PermissionInfo;
import com.eipulse.teamproject.entity.employee.Title;
import com.eipulse.teamproject.entity.employee.TitleMove;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.employeerepository.TitleMoveRepository;
import com.eipulse.teamproject.repository.employeerepository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {


	private SecurityConfig securityConfig;
	private TitleRepository titleRepository;
	private JavaMailSender javaMailSender;
	private EmployeeRepository empRepo;
	private TitleMoveService titleMoveService;
	private TitleMoveRepository moveRepo;


	@Autowired
	public EmployeeService(SecurityConfig securityConfig, TitleRepository titleRepository, JavaMailSender javaMailSender, EmployeeRepository empRepo, TitleMoveService titleMoveService, TitleMoveRepository moveRepo) {
		this.securityConfig = securityConfig;
		this.titleRepository = titleRepository;
		this.javaMailSender = javaMailSender;
		this.empRepo = empRepo;
		this.titleMoveService = titleMoveService;
		this.moveRepo = moveRepo;
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

	// 變更員工職位
	@Transactional
	public EmpDTO updateEmpTitle (TitleMoveDTO moveDTO){
		// 查詢員工
		Employee emp = empRepo.findById(moveDTO.getEmpId())
				.orElseThrow(()-> new RuntimeException("無此員工"));
		// 查詢新職位
		Title newTitle = titleRepository.findById(moveDTO.getTitleId())
				.orElseThrow(() -> new RuntimeException("無此職位"));

		// 判斷是否輸入一樣的職位
		if (emp.getTitle().getId() != newTitle.getId()){
			// 如果成功會自動增加職位異動紀錄
			moveRepo.save(new TitleMove(emp,emp.getTitle().getTitleName(),newTitle.getTitleName(),moveDTO.getReason(),moveDTO.getEffectDate(),moveDTO.getApprover()));
			// 再把新的職位更新到員工的職位
			emp.setTitle(newTitle);
		}else {
			return null;
		}
		return  new EmpDTO(empRepo.save(emp));
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



	// 員工登入
	public EmpDTO empLogin(EmpDTO empDTO){
		Optional<Employee> optional = empRepo.findById(empDTO.getEmpId());

		// 初始化一個用於儲存權限ID的集合
		Set<Integer> permissionIds = new LinkedHashSet<>();

		optional.ifPresentOrElse(
				employee -> {
					// TODO: 使用更安全的方式比對密碼，例如bcrypt
					if(empDTO.getPassword() != null && employee.getPassword() != null
							&& empDTO.getPassword().equals(employee.getPassword()) ||securityConfig.passwordEncoder().matches(empDTO.getPassword(), employee.getPassword())) {
						empDTO.setEmpName(employee.getEmpName());
						empDTO.setEmail(employee.getEmail());
						// 獲取員工的所有權限ID
						for(PermissionInfo permissionInfo : employee.getPermissionInfos()) {
							permissionIds.add(permissionInfo.getPermission().getPermissionId());
						}

						// 設置權限ID
						empDTO.setPermissionId(permissionIds);
					} else {
						// TODO: 抛出更具體的異常
						throw new SecurityException("密碼不匹配");
					}
				},
				() -> {
					// TODO: 抛出更具體的異常
					throw new NoSuchElementException("用戶ID不存在");
				}
		);

		return empDTO;
	}


	public List<EmpDTO> findSameDeptEmp(Integer empId){
		Employee employee = empRepo.findById(empId).orElseThrow(()->new RuntimeException("error"));
		return empRepo.findSameDeptEmp(employee.getTitle().getDept().getDeptId());
	}


	public EmpDTO forgetPassword(EmpDTO empDTO,Integer otpVal){
		Optional<Employee> optional = empRepo.findByEmail(empDTO.getEmail());
		if (optional.isPresent()) {
			Employee employee = optional.get();
//進階版Thread
			CompletableFuture.runAsync(()->{
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("anddie0904@gmail.com");
				message.setTo(employee.getEmail());
				message.setSubject("Eipulse員工驗證碼");
				message.setText("您的驗證碼：" + otpVal);
				javaMailSender.send(message);
			}).exceptionally(ex->{
				throw new UnsupportedOperationException("mail發送失敗");
			});
			empDTO.setEmpId(employee.getEmpId());
			return empDTO;
		}
		throw new UnsupportedOperationException("無此email");
	}

	public boolean newPassword(EmpDTO empDTO) {
		Employee employee = empRepo.findById(empDTO.getEmpId()).orElseThrow(()->new RuntimeException("error"));

		employee.setPassword(securityConfig.passwordEncoder().encode(empDTO.getNewPassword()));
		empRepo.save(employee);
		return true;
	}

	public Employee getEmpLineId(String empLineId){
		Optional<Employee> optional = empRepo.findByEmpLineId(empLineId);
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}
	public Employee saveEmpLineId(Integer empId,String email,String lineId){
		Optional<Employee>optional = empRepo.findById(empId);
		if(optional.isPresent()){
			Employee employee= optional.get();
			if(employee.getEmail().equals(email)){
				employee.setEmpLineId(lineId);
				return employee;
			}
		}
		throw new RuntimeException("Error");
	}

}
