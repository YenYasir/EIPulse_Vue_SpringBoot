package com.eipulse.teamproject.repository.employeerepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eipulse.teamproject.dto.employeedto.EmpDTO;
import com.eipulse.teamproject.entity.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// 模糊搜尋員工姓名
	@Query("FROM Employee emp WHERE emp.empName LIKE %?1% ")
	List<EmpDTO> findByNameLikeSearch(String name);

	// 分頁功能
	@Query("SELECT emp FROM Employee emp WHERE emp.empId > :id ORDER BY emp.empId")
	public Page<Employee> findByEmpIdPage(@Param("id") Integer id, Pageable pageable);

	// 分頁功能 by name
	@Query("SELECT emp FROM Employee emp WHERE emp.empName Like %?1% ORDER BY emp.empId")
	public Page<Employee> findByNamePage(@Param("name") String name, Pageable pageable);

	@Query("from Employee e where e.email=?1")
	Optional<Employee> findByEmail(String email);

	// 簽核用
	@Query("select e.empId,e.empName from Employee e where e.title.dept.deptId=?1")
	List<Object> findSameDeptEmp(Integer deptId);

	@Query("from Employee e where e.empLineId=?1")
	Optional<Employee> findByEmpLineId(String empLineId);

	@Query("select e.empId,e.empName,e.photoUrl from Employee e where  e.empId!=:empId")
	List<Object> exceptForMyself(Integer empId);

}