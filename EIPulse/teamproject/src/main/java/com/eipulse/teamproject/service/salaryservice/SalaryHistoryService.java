package com.eipulse.teamproject.service.salaryservice;

import com.eipulse.teamproject.dto.salarydto.SalaryHistoryDto;
import com.eipulse.teamproject.entity.salaryentity.EmpSalaryInfo;
import com.eipulse.teamproject.entity.salaryentity.SalaryHistory;
import com.eipulse.teamproject.repository.salaryrepository.EmpSalaryInfoRepository;
import com.eipulse.teamproject.repository.salaryrepository.SalaryHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SalaryHistoryService {

	private EmpSalaryInfoRepository empSlRepo;
	private SalaryHistoryRepository salaryHistoryRepo;

	@Autowired
	public SalaryHistoryService(EmpSalaryInfoRepository empSlRepo, SalaryHistoryRepository salaryHistoryRepo) {
		this.empSlRepo = empSlRepo;
		this.salaryHistoryRepo = salaryHistoryRepo;
	}

	// 新增異動紀錄 ok
	public SalaryHistoryDto setHistory(SalaryHistoryDto historyDto) {
		Integer empId = historyDto.getEmpId();
		Integer adjustSalary = historyDto.getAdjustSalary();
		EmpSalaryInfo empSalaryInfo = empSlRepo.findById(empId).get();
		Integer oldSalary = empSalaryInfo.getBasicSalary();
		if (!adjustSalary.equals(oldSalary)) {
			historyDto.setOriginalSalary(oldSalary);
			SalaryHistory saveResult = salaryHistoryRepo.save(new SalaryHistory(historyDto));
			String empName = empSalaryInfo.getEmployee().getEmpName();
			return new SalaryHistoryDto(saveResult, empName);
		} else {
			return null;
		}

	}

	// 查詢單一員工薪資異動紀錄 ok
	public List<SalaryHistoryDto> findHistoryByEmpId(Integer empId) {
		List<SalaryHistory> salaryHistoryList = salaryHistoryRepo.findByEmpId(empId);
		List<SalaryHistoryDto> listDto = new ArrayList<>();
		for (SalaryHistory history : salaryHistoryList) {
			SalaryHistoryDto historyDto = new SalaryHistoryDto(history);
			listDto.add(historyDto);
		}
		return listDto;
	}


	// 刪除 ok
	public String delete(Integer id) {
		salaryHistoryRepo.deleteById(id);
		return "刪除成功";
	}

	public SalaryHistoryService() {
		// TODO Auto-generated constructor stub
	}

}
