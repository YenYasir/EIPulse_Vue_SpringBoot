//package com.eipulse.teamproject.serviceimp;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.eipulse.teamproject.entitys.Dept;
//import com.eipulse.teamproject.entitys.Employee;
//import com.eipulse.teamproject.entitys.Login;
//import com.eipulse.teamproject.repository.EmployeeRepository;
//import com.eipulse.teamproject.repository.LoginRepository;
//import com.eipulse.teamproject.service.EmployeeService;
//
//@Transactional
//@Service
//public class EmployeeServiceImp implements EmployeeService {
//
//	private EmployeeRepository employeeRepository;
//
//	private LoginRepository loginRepository;
//
//	private PasswordEncoder passwordEncoderl;
//
//	@Autowired
//	public EmployeeServiceImp(EmployeeRepository employeeRepository, LoginRepository loginRepository,
//			PasswordEncoder passwordEncoderl) {
//		this.employeeRepository = employeeRepository;
//		this.loginRepository = loginRepository;
//		this.passwordEncoderl = passwordEncoderl;
//	}
//
//	private String generateSequence() {
//
//		return "001";
//	}
//
//	// Employee ID: 加入年末2碼+部門編號+加入序列
//	@Override
//	public void createEmpId(Employee employee, Dept dept) {
//
//		LocalDate hirdate = employee.getHireDate();
//		String year = Integer.toString(hirdate.getYear() - 1911);
//		Integer deptId = dept.getDeptId();
//		String sequence = generateSequence();
//		String employeeId = year + deptId.toString() + sequence;
//		Employee newemployee = new Employee();
//		employee.setEmpId(Integer.parseInt(employeeId));
//		employeeRepository.save(newemployee);
//	}
//
//	@Override
//	public void addEmp(Employee emp) {
//		employeeRepository.save(emp);
//	}
//
//	@Override
//	public Employee saveEmp(Employee employee) {
//		if (employee != null) {
//			LocalDate birth = employee.getBirth();
//			String password = String.format("%02d", birth.getMonthValue())
//					+ String.format("%02d", birth.getDayOfMonth());
//			Employee newEmp = employeeRepository.save(employee);
//
//			Login login = new Login();
//			login.setEmpId(newEmp.getEmpId());
//			login.setPassWord(passwordEncoderl.encode(password));
//			loginRepository.save(login);
//			return employee;
//		}
//		return null;
//	}
//
//	@Override
//	public Employee findEmpById(Integer empId) {
//		Optional<Employee> optional = employeeRepository.findById(empId);
//		if (optional != null) {
//			return optional.get();
//		}
//		return null;
//	}
//
//	@Override
//	public List<Employee> findEmpAll() {
//
//		return employeeRepository.findAll();
//	}
//
//	@Override
//	public boolean updateEmp(Integer empId, Employee newEmployee) {
//		Optional<Employee> optional = employeeRepository.findById(empId);
//
//		if (optional != null) {
//			Employee oldEmployee = optional.get();
//			if (oldEmployee != newEmployee) {
//				employeeRepository.save(newEmployee);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public boolean deleteEmp(Integer empId) {
//		employeeRepository.deleteById(empId);
//		return true;
//	}
//}
