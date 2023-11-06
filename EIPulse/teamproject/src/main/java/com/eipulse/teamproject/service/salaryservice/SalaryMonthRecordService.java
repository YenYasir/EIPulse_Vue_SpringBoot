package com.eipulse.teamproject.service.salaryservice;

import com.eipulse.teamproject.dto.salarydto.*;
import com.eipulse.teamproject.entity.salaryentity.EmpSalaryInfo;
import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import com.eipulse.teamproject.entity.salaryentity.SalaryMonthRecord;
import com.eipulse.teamproject.entity.salaryentity.SubjectType;
import com.eipulse.teamproject.repository.salaryrepository.SalaryMonthRecordRepository;
import com.eipulse.teamproject.repository.salaryrepository.SubjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class SalaryMonthRecordService {

	private EmpSalaryInfoService infoService;
	private SalaryMonthRecordRepository recordRepo;
	private SubjectTypeRepository subjectRepo;

	@Autowired
	public SalaryMonthRecordService(EmpSalaryInfoService infoService, SalaryMonthRecordRepository recordRepo, SubjectTypeRepository subjectRepo) {
		this.infoService = infoService;
		this.recordRepo = recordRepo;
		this.subjectRepo = subjectRepo;
	}

	// 產生試算表(本薪/伙食津貼/勞健保費/勞退自提/福委會扣)，其他變動項目未產生
	public List<SalaryTrialDto> generateSalaryTrialCalculation(LocalDate date) {

		// 前端輸入計算區間日期(終日)，找在終日(含)到在職員工
		List<EmpSalaryInfo> infoList = infoService.findByDate(date);

		// 要返回的物件
		List<SalaryTrialDto> trialList = new ArrayList<>();

		for (EmpSalaryInfo info : infoList) {
			String empName = info.getEmployee().getEmpName();
			LocalDate hiredate = info.getEmployee().getHireDate();
			SalaryInfoDto infoDto = new SalaryInfoDto(info,empName,hiredate);
			SalaryTrialDto trialDto = createSalaryTrialDto(infoDto, date);

			trialList.add(trialDto);
		}
		return trialList;
	}
	
	// 產生SalaryTrialDto
	private SalaryTrialDto createSalaryTrialDto(SalaryInfoDto infoDto, LocalDate date) {
		LocalDate hiredate = infoDto.getHireDate();
		Integer empId = infoDto.getEmpId();
		String empName = infoDto.getEmpName();

		Integer year = date.getYear();
		Integer month = date.getMonthValue();

		boolean isFullMonth = isFullMonth(hiredate);
		List<DetailDto> details = null;
		Integer addSum = 0;
		Integer deduSum = 0;
		Integer netSalary=0;
		Integer grossSalary=0;

		if (isFullMonth) {
			// 足月
			details = calculateFullMonth(infoDto);

			for (DetailDto d : details) {
				// 計算加減項總和
				SubjectType subject = subjectRepo.findById(d.getSubjectId()).get();
				if (subject.getCalculateType().equals("P")) {
					addSum += d.getAmount();
				} else if (subject.getCalculateType().equals("M")) {
					deduSum += d.getAmount();
				}
				netSalary= addSum-deduSum;
			}
		} else {
			// 不足月
			details = calculateMonthProrated(infoDto);
			for (DetailDto d : details) {
				// 計算加減項總和
				SubjectType subject = subjectRepo.findById(d.getSubjectId()).get();
				if (subject.getCalculateType().equals("P")) {
					addSum += d.getAmount();
				} else if (subject.getCalculateType().equals("M")) {
					deduSum += d.getAmount();}
				netSalary= addSum-deduSum;
		}
		}
		return new SalaryTrialDto(new SalaryMonthRecordDto(empId,empName,year,month,addSum,deduSum,grossSalary,netSalary),details);
	}
	
	// 判斷足月或不足月 true( >=0 )==>足月 ; false ( <0 )==> 不足月
	private Boolean isFullMonth(LocalDate hireDate) {
		LocalDate nowDate = LocalDate.now();
		LocalDate baseMonth = null;

		if (nowDate.getMonthValue() == 1) {
			baseMonth = LocalDate.of(nowDate.getYear() - 1, 12, 1);
		} else {
			baseMonth = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue() - 1, 1);
		}
		long value = hireDate.until(baseMonth, ChronoUnit.DAYS);
		System.out.println("判斷值:" + value);
		if (value >= 0) {
			return true;
		} else {
			return false;
		}

	}

	// 員工足月費用計算 ( 健保/福利金/勞保/勞退 )
	private List<DetailDto> calculateFullMonth(SalaryInfoDto infoDto) {

		Integer empId = infoDto.getEmpId();
		Integer basicSalary = infoDto.getBasicSalary();
		Integer num = infoDto.getFamilyDependantsNum();
		String welfare = infoDto.getWelfareBenefitsDeduction();
		Integer healthGrade = infoDto.getHealthInsuranceGrade();
		Integer laborGrade = infoDto.getLaborInsuranceGrade();
		Double laborRate = infoDto.getLaborVolunteerPensionRate();

		// 計算費用
		Integer healthInsuranceFee = infoService.calculateHealthInsuranceFee(num, healthGrade);
		Integer laborFee = infoService.calculateLaborInsuranceFee(laborGrade);
		Integer laborVolunteerFee = infoService.calculateLaborVolunteerPensionFee(laborRate, laborGrade);
		Integer welfareFee = infoService.calculateWelfareFee(welfare, basicSalary);


		List<DetailDto> detailList = new ArrayList<>();
		
		// 基本薪資
		DetailDto salaryDto = new DetailDto(empId,1000, basicSalary);
		detailList.add(salaryDto);
		// 勞保費
		DetailDto laborDto = new DetailDto(empId,1004, laborFee);
		detailList.add(laborDto);
		// 勞退自提
		DetailDto laborVolDto = new DetailDto(empId,1006, laborVolunteerFee);
		detailList.add(laborVolDto);
		// 福利金
		DetailDto welfDto = new DetailDto(empId,1007, welfareFee);
		detailList.add(welfDto);
		// 健保費
		DetailDto healthDto = new DetailDto(empId,1005, healthInsuranceFee);
		detailList.add(healthDto);

		return detailList;


	}


	// 員工不足月費用計算 ( 健保/福利金/勞保/勞退 )
	private List<DetailDto> calculateMonthProrated(SalaryInfoDto infoDto) {
		
		Integer empId = infoDto.getEmpId();
		LocalDate hiredate = infoDto.getHireDate();
		Integer basicSalary = infoDto.getBasicSalary();
		Integer num = infoDto.getFamilyDependantsNum();
		String welfare = infoDto.getWelfareBenefitsDeduction();
		Integer healthGrade = infoDto.getHealthInsuranceGrade();
		Integer laborGrade = infoDto.getLaborInsuranceGrade();
		Double laborRate = infoDto.getLaborVolunteerPensionRate();

		// 計算費用
		Integer laborFee = infoService.calculateLaborInsuranceFeeProrated(hiredate, laborGrade);
		Integer laborVolunteerFee = infoService.calculateLaborVolunteerPensionFeeProrated(hiredate, laborRate,
				laborGrade);
		Integer welfareFee = infoService.calculateWelfareFeeProrated(hiredate, welfare, basicSalary);
		Integer basicSalaryProrated = infoService.calculateSalaryProrated(hiredate, basicSalary);
		Integer healthInsuranceFee = infoService.calculateHealthInsuranceFee(healthGrade, num);


		List<DetailDto> detailList = new ArrayList<>();

		// 基本薪資
		DetailDto salaryDto = new DetailDto(empId,1000, basicSalaryProrated);
		detailList.add(salaryDto);
		// 勞保費
		DetailDto laborDto = new DetailDto(empId,1004, laborFee);
		detailList.add(laborDto);
		// 勞退自提
		DetailDto laborVolDto = new DetailDto(empId,1006, laborVolunteerFee);
		detailList.add(laborVolDto);
		// 福利金
		DetailDto welfDto = new DetailDto(empId,1007, welfareFee);
		detailList.add(welfDto);
		// 健保費
		DetailDto healthDto = new DetailDto(empId,1005, healthInsuranceFee);
		detailList.add(healthDto);

		return detailList;


	}
	
	// 第一次儲存 ==>Controller使用的主Service
	@Transactional
	@Scheduled(cron="0 25 09 * * *")
	public List<SalaryTrialDto> saveMonthRecordAndDetail() {
		LocalDate date = LocalDate.now();
		List<SalaryTrialDto> salaryTrialsDtos = generateSalaryTrialCalculation(date);

		// 裝要回傳前端DTO物件
		List<SalaryTrialDto> result = new ArrayList<>();
		

		for (SalaryTrialDto finalDto : salaryTrialsDtos) {

			List<SalaryDetail> detailList = new ArrayList<>();
			// 取出明細與紀錄表dto
		SalaryMonthRecordDto salaryMonthRecordDto = finalDto.getSalaryMonthRecordDto();
		SalaryMonthRecord salaryMonthRecord = new SalaryMonthRecord(salaryMonthRecordDto);
		List<DetailDto> detaildDtos = finalDto.getDetaildDto();
	


			for(DetailDto dDto: detaildDtos) {
				SalaryDetail salaryDetail = new SalaryDetail(dDto);
				detailList.add(salaryDetail);
			}
			
			salaryMonthRecord.setSalaryDetails(detailList);
			SalaryMonthRecord saveResult = recordRepo.save(salaryMonthRecord);

			List<SalaryDetail> salaryDetails = saveResult.getSalaryDetails();
			SalaryTrialDto d = new SalaryTrialDto(saveResult,salaryDetails);

			result.add(d);
		}
		return result;
	
	}

	// 用紀錄表id找
	public SalaryMonthRecord findByRecordId(Integer Id) {
		SalaryMonthRecord result = recordRepo.findById(Id).get();
		return result;

	}

	
	// 改變狀態變成已計薪 ok ==>表單不能更新及刪除
	public List<SalaryTrialFinalDto> transStatusEnabled(List<SalaryTrialFinalDto> finalDtos) {

		List<SalaryTrialFinalDto> returnDto = new ArrayList<>();

		// 取出每位員工一筆月紀錄
		for (SalaryTrialFinalDto finalDto : finalDtos) {
			Integer recordId = finalDto.getSalaryMonthRecordDto().getId();
			Integer empId = finalDto.getSalaryMonthRecordDto().getEmpId();

			Integer count = recordRepo.translateStatusUnabled(recordId, empId);

			if (count > 0) {
				SalaryMonthRecord salaryMonthRecord = recordRepo.findById(recordId).get();
				List<SalaryDetail> salaryDetails = salaryMonthRecord.getSalaryDetails();
				returnDto.add(new SalaryTrialFinalDto(salaryMonthRecord, salaryDetails));
			}
		}
		return returnDto;
	}

	// 員工查詢歷史薪資明細/月紀錄(已計薪) ok
	public List<SalaryTrialFinalDto> findAllByEmpId(Integer EmpId) {

		List<SalaryTrialFinalDto> finalDtoList = new ArrayList<>();
		// 取出月紀錄表與相關明細
		List<SalaryMonthRecord> recordResult = recordRepo.findByEmpId(EmpId);
		// 一個員工一個月會有一筆月紀錄及多筆明細
		int size = recordResult.size();
		System.out.println("多長:" + size);
		for (int i = 0; i < size; i++) {
			SalaryMonthRecord salaryMonthRecord = recordResult.get(i);
			List<SalaryDetail> salaryDetails = salaryMonthRecord.getSalaryDetails();

			SalaryTrialFinalDto salaryTrialFinalDto = new SalaryTrialFinalDto(salaryMonthRecord, salaryDetails);
			finalDtoList.add(salaryTrialFinalDto);
		}
		return finalDtoList;
	}
	
	// 找當月份all
	public List<SalaryTrialDto> findAllByMonthAndYear(LocalDate date){
		Integer year = date.getYear();
		Integer month = date.getMonthValue();
		List<SalaryTrialDto> resultList = new ArrayList<>();
		List<SalaryMonthRecord> result = recordRepo.findBySlYearAndSlMonth(year, month);
		for (SalaryMonthRecord mothtRecord: result) {
			List<SalaryDetail> salaryDetails = mothtRecord.getSalaryDetails();
			SalaryTrialDto salaryTrialDto = new SalaryTrialDto(mothtRecord,salaryDetails);
			
			resultList.add(salaryTrialDto);		
		}
		return resultList;
	}
	// 找單
	public SalaryTrialDto findById(Integer id) {
		SalaryMonthRecord salaryMonthRecord = recordRepo.findById(id).get();
		List<SalaryDetail> salaryDetails = salaryMonthRecord.getSalaryDetails();
		SalaryTrialDto salaryTrialDto = new SalaryTrialDto(salaryMonthRecord,salaryDetails);
		return salaryTrialDto;
	}
	
	// 更新單一員工紀錄表與明細表
	public SalaryTrialDto update(SalaryTrialDto trialDto) {
		SalaryMonthRecordDto salaryMonthRecordDto = trialDto.getSalaryMonthRecordDto();
		List<DetailDto> detaildDtos = trialDto.getDetaildDto();
		List<SalaryDetail> detailList = new ArrayList<>();
		SalaryMonthRecord monthRecord = new SalaryMonthRecord(salaryMonthRecordDto);
		for (DetailDto  dDto :detaildDtos) {
			SalaryDetail salaryDetail = new SalaryDetail(dDto);
			detailList.add(salaryDetail);
		}
		monthRecord.setSalaryDetails(detailList);
		SalaryMonthRecord saveResult = recordRepo.save(monthRecord);
		List<SalaryDetail> detailResult = saveResult.getSalaryDetails();
      return new SalaryTrialDto(saveResult,detailResult);
	}
}
