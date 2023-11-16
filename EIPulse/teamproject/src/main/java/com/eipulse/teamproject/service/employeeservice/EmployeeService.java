package com.eipulse.teamproject.service.employeeservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.eipulse.teamproject.entity.employee.*;
import com.eipulse.teamproject.repository.employeerepository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eipulse.teamproject.config.SecurityConfig;
import com.eipulse.teamproject.dto.employeedto.EmpDTO;
import com.eipulse.teamproject.dto.employeedto.TitleMoveDTO;
import com.eipulse.teamproject.entity.view.EmployeesName;

@Service
public class EmployeeService {

 private SecurityConfig securityConfig;
 private TitleRepository titleRepository;
 private JavaMailSender javaMailSender;
 private EmployeeRepository empRepo;
 private TitleMoveService titleMoveService;
 private TitleMoveRepository moveRepo;
 private EmployeesNameRepository employeesNameRepository;
 private PermissionRepository permissionRepository ;
 private PermissionInfoRepository infoRepository;

 @Autowired
 public EmployeeService(SecurityConfig securityConfig, TitleRepository titleRepository, JavaMailSender javaMailSender, EmployeeRepository empRepo, TitleMoveService titleMoveService, TitleMoveRepository moveRepo, EmployeesNameRepository employeesNameRepository, PermissionInfoRepository infoRepository,PermissionRepository permissionRepository) {
  this.securityConfig = securityConfig;
  this.titleRepository = titleRepository;
  this.javaMailSender = javaMailSender;
  this.empRepo = empRepo;
  this.titleMoveService = titleMoveService;
  this.moveRepo = moveRepo;
  this.employeesNameRepository = employeesNameRepository;
  this.infoRepository = infoRepository;
  this.permissionRepository = permissionRepository;
 }




 // add
 public void addEmp(EmpDTO empDTO) {
  Employee employee= empRepo.save(new Employee(empDTO));
  Permission permission =permissionRepository.findById(1).get();
  PermissionInfo permissionInfo = new PermissionInfo(employee,permission);
  infoRepository.save(permissionInfo);
 }

 // find once
 public EmpDTO findById(Integer id) {

  Employee emp = empRepo.findById(id).orElseThrow(() -> new RuntimeException("查無此資料"));

  return new EmpDTO(emp.getEmpId(), emp.getEmpName(), emp.getBirth(), emp.getEmail(), emp.getIdNumber(),
    emp.getGender(), emp.getPhone(), emp.getTel(), emp.getPhotoUrl(), emp.getAddress(),
    emp.getTitle().getTitleName(), emp.getEmpState(), emp.getHireDate(), emp.getLeaveDate(),
    emp.getEditDate());
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
     emp.getEditDate());
   result.add(dto);
  }
  return result;
 }

 // find only name
 public List<EmployeesName> findAllName() {
  return employeesNameRepository.findAll();
 }

 // findByNameLikeSearch
 public List<EmpDTO> findByNameLikeSearch(String name) {
  List<EmpDTO> list = empRepo.findByNameLikeSearch(name);
  return list;
 }

 // update
 public EmpDTO updateEmp(EmpDTO empDTO, MultipartFile file) {
  Employee employee = empRepo.findById(empDTO.getEmpId())
    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + empDTO.getEmpId()));

  // 更新employee對象的屬性
  employee.setEmpName(empDTO.getEmpName());
  employee.setPhone(empDTO.getPhone());
  employee.setEmail(empDTO.getEmail());
  employee.setPhotoUrl(empDTO.getPhotoUrl());
  employee.setAddress(empDTO.getAddress());
  employee.setTel(empDTO.getTel());

  // 保存並返回更新後的employee
  return new EmpDTO(empRepo.save(employee));
 }

 // 變更頭像
 public EmpDTO updateAvatar(EmpDTO empDTO, MultipartFile file) {
  Employee employee = empRepo.findById(empDTO.getEmpId())
    .orElseThrow(() -> new RuntimeException("找不到此員工編號的員工: " + empDTO.getEmpId()));
  try {
   String uploadPath = "${upload.path}";
   String fileName = "avatar_" + empDTO.getEmpId() + "_" + file.getOriginalFilename();
   String filePath = uploadPath + "/" + fileName;

   File localFile = new File(filePath);
   file.transferTo(localFile);

   employee.setPhotoUrl(filePath);
   empRepo.save(employee);
   return new EmpDTO(empRepo.save(employee));
  } catch (IOException e) {

  }
  return empDTO;
 }

 // 變更員工職位
 @Transactional
 public EmpDTO updateEmpTitle(TitleMoveDTO moveDTO) {
  // 查詢員工
  Employee emp = empRepo.findById(moveDTO.getEmpId()).orElseThrow(() -> new RuntimeException("無此員工"));
  // 查詢新職位
  Title newTitle = titleRepository.findById(moveDTO.getTitleId()).orElseThrow(() -> new RuntimeException("無此職位"));

  // 判斷是否輸入一樣的職位
  if (emp.getTitle().getId() != newTitle.getId()) {
   // 如果成功會自動增加職位異動紀錄
   moveRepo.save(new TitleMove(emp, emp.getTitle().getTitleName(), newTitle.getTitleName(),
     moveDTO.getReason(), moveDTO.getEffectDate(), moveDTO.getApprover()));
   // 再把新的職位更新到員工的職位
   emp.setTitle(newTitle);
  } else {
   return null;
  }
  return new EmpDTO(empRepo.save(emp));
 }

 // 分頁功能
 public Page<EmpDTO> findByPage(Integer pageNumber) {
  Pageable pgb = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "empId");
  Page<EmpDTO> result = empRepo.findByEmpIdPage(pgb);
  return result;
 }

 // 模糊搜尋分頁功能
 public Page<EmpDTO> findByNamePage(Integer pageNumber, String name) {
  Pageable pgb = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "empId");
  Page<Employee> page = empRepo.findByNamePage(name, pgb);
  Page<EmpDTO> result = page.map(employee -> new EmpDTO(employee));
  return result;
 }

 // 員工登入
 public EmpDTO empLogin(EmpDTO empDTO) {
  Optional<Employee> optional = empRepo.findById(empDTO.getEmpId());

  optional.ifPresentOrElse(employee -> {
   // 使用更安全的方式比對密碼，例如bcrypt
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
    empDTO.setTitleName(employee.getTitle().getTitleName());
    empDTO.setDeptName(employee.getTitle().getDept().getDeptName());
    empDTO.setPhotoUrl(employee.getPhotoUrl());
    empDTO.setHireDate(employee.getHireDate());
    // 設置權限ID
    empDTO.setPermissionId(employee.getPermissionInfo().getPermission().getPermissionId());
   } else {
    // 抛出更具體的異常
    throw new SecurityException("密碼不匹配");
   }
  }, () -> {
   // 抛出更具體的異常
   throw new NoSuchElementException("員工代號不存在");
  });

  return empDTO;
 }

 // 查詢同部門
 public List<EmpDTO> findSameDeptEmp(Integer empId) {
  Employee employee = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("error"));
  return empRepo.findSameDeptEmp(employee.getTitle().getDept().getDeptId());
 }

 //  簽核專用，查詢同部門
 public List<Object> findFormSameDeptEmp(Integer empId) {
  Employee employee = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("error"));
  return empRepo.findFormSameDeptEmp(employee.getTitle().getDept().getDeptId());
 }
 
 // 忘記密碼
  public EmpDTO forgetPassword(EmpDTO empDTO, Integer otpVal) {
    Optional<Employee> optional = empRepo.findByEmail(empDTO.getEmail());
    if (optional.isPresent()) {
     Employee employee = optional.get();
  //進階版Thread
     CompletableFuture.runAsync(() -> {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("EIPulse 科技 <blueyykai@gmail.com>");
      message.setTo(employee.getEmail());
      message.setSubject("Eipulse員工驗證碼");
      message.setText("您的驗證碼為：" + otpVal);
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

 public Employee getEmpLineId(String empLineId) {
  Optional<Employee> optional = empRepo.findByEmpLineId(empLineId);
  if (optional.isPresent()) {
   return optional.get();
  }
  return null;
 }

 public Employee saveEmpLineId(Integer empId, String email, String lineId) {
  Optional<Employee> optional = empRepo.findById(empId);
  if (optional.isPresent()) {
   Employee employee = optional.get();
   if (employee.getEmail().equals(email)) {
    employee.setEmpLineId(lineId);
    return employee;
   }
  }
  throw new RuntimeException("Error");
 }

}