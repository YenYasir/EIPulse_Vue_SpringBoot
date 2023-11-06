package com.eipulse.teamproject.service.salaryservice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import com.eipulse.teamproject.dto.salarydto.SalaryInfoDto;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.salaryentity.EmpSalaryInfo;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.salaryrepository.EmpSalaryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.eipulse.teamproject.constant.SalaryParamEnum;


@Service
public class EmpSalaryInfoService {

    private EmpSalaryInfoRepository empSlRepo;
    private EmployeeRepository empRepo;


    @Autowired
    public EmpSalaryInfoService(EmpSalaryInfoRepository empSlRepo, EmployeeRepository empRepo) {
        this.empSlRepo = empSlRepo;
        this.empRepo = empRepo;
    }

    // 找有無該員工編號員工(使用Employee repo)
    public Employee findById(Integer empId) {
        Optional<Employee> optional = empRepo.findById(empId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    //查詢某員工是否以建立薪資資訊
    public Boolean existsById(Integer empId) {
        return empSlRepo.existsById(empId);
    }

    // 建立新員工薪資資訊(Dto)  ok
    public SalaryInfoDto createNewEmpSalayInfo(SalaryInfoDto infoDto) {
        Integer empId = infoDto.getEmpId();
        EmpSalaryInfo info = new EmpSalaryInfo(infoDto);
        EmpSalaryInfo infoResult = empSlRepo.save(info);

        String value = infoResult.getWelfareBenefitsDeduction();

        //使用Map轉換資料庫回傳1/2
        Map<String, String> deductionMap = new HashMap<>();
        deductionMap.put("1", "是");
        deductionMap.put("0", "否");
        infoResult.setWelfareBenefitsDeduction(deductionMap.get(value));

        String empName = empRepo.findById(empId).get().getEmpName();
        infoResult.setEmpName(empName);
        return new SalaryInfoDto(infoResult);

    }


    // 搜尋單一員工薪資資訊(Dto)
    public SalaryInfoDto findInfoById(Integer empId) {
        Optional<EmpSalaryInfo> optional = empSlRepo.findById(empId);
        if (optional.isPresent()) {
            EmpSalaryInfo empSalaryInfo = optional.get();

            return new SalaryInfoDto(empSalaryInfo);

        }
        return null;
    }

    // 名字模糊搜尋
    public List<SalaryInfoDto> findInfoByName(String empName) {
        List<EmpSalaryInfo> infoList = empSlRepo.findByEmpNameLike(empName);
        List<SalaryInfoDto> resultDto = new ArrayList<>();
        for (EmpSalaryInfo info : infoList) {
            SalaryInfoDto salaryInfo = new SalaryInfoDto(info);
            resultDto.add(salaryInfo);
            return resultDto;

        }
        return null;
    }


    // 查詢所有員工薪資資訊(dto) ok
    public List<SalaryInfoDto> findAllEmpSalaryInfo() {
        List<EmpSalaryInfo> result = empSlRepo.findAll();
        List<SalaryInfoDto> resultDto = new ArrayList<>();

        for (EmpSalaryInfo info : result) {
            String yn = info.getWelfareBenefitsDeduction();
            Map<String, String> deductionMap = new HashMap<>();
            deductionMap.put("1", "是");
            deductionMap.put("0", "否");
            info.setWelfareBenefitsDeduction(deductionMap.get(yn));


            SalaryInfoDto salaryInfo = new SalaryInfoDto(info);
            resultDto.add(salaryInfo);
        }
        return resultDto;

    }

    // 分頁 (一頁10筆)
    public Page<SalaryInfoDto> findByPage(Integer pageNumber) {

        Pageable pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "empId");
        Page<EmpSalaryInfo> page = empSlRepo.findAll(pgb);
        return page.map(info -> new SalaryInfoDto(info));


    }


    //查詢薪資結算終日前之員工之薪資資訊
    public List<EmpSalaryInfo> findByDate(LocalDate date) {
        List<EmpSalaryInfo> infoList = empSlRepo.findInfoBeforeEndDate(date);
        return infoList;
    }

    // 計算個人健保費用(以月計，分有無眷屬加保)
    public Integer calculateHealthInsuranceFee(Integer healthGrade, Integer familyNum) {
        Double healthEE = SalaryParamEnum.HEALTHEE.getValue();
        Double healthInsurance = SalaryParamEnum.HEALTHINSURANCE.getValue();


        // healthInsuranceRank轉換為浮點數，以確保計算的精確性
        Integer healthInsuranceFee;
        if (familyNum == 0) {
            healthInsuranceFee = (int) Math.round(healthGrade.doubleValue() * healthEE * healthInsurance);
        } else {
            healthInsuranceFee = (int) Math.round(healthGrade * healthEE * healthInsurance * (1 + familyNum));
        }
        return healthInsuranceFee;
    }

    // (足月)勞保費用
    public Integer calculateLaborInsuranceFee(Integer laborGrade) {
        Double empInsurance = SalaryParamEnum.EMPINSURANCE.getValue(); //就保費率
        Double laborEE = SalaryParamEnum.LABOREE.getValue(); //勞工負擔%

        Integer fee = (int) Math.round(laborGrade * empInsurance * laborEE);
        return fee;
    }

    // (不足月)計算勞保費用(依到職月大小月有不同收費方式)
    public Integer calculateLaborInsuranceFeeProrated(LocalDate hireDate, Integer laborGrade) {
        Double value = SalaryParamEnum.EMPINSURANCE.getValue(); //就保費率
        Double laborEE = SalaryParamEnum.LABOREE.getValue(); //勞工負擔%
        // 取得到職之"日"
        int dd = hireDate.getDayOfMonth();
        // (30 − 加保日＋1＝該月保費計收日數)，而30日加保或31日加保，保險費均計收1日==>此公式不用再判斷閏年，指判斷當月是30或31天
        Double workDays = (double) (30 - dd + 1);

        if (workDays > 0) {
            Integer laborInsuranceFee = (int) Math.round(laborGrade * value * laborEE * workDays / 30);
            return laborInsuranceFee;
        } else {
            Integer laborInsuranceFee = (int) Math.round(laborGrade * value * laborEE * (workDays + 1) / 30);
            return laborInsuranceFee;
        }
    }


    // (足月)計算勞退自提費用
    public Integer calculateLaborVolunteerPensionFee(Double laborVolunteerPensionRate, Integer laborInsuranceGrade) {

        Integer laborVolunteerPensionFee = (int) Math.round(laborInsuranceGrade * laborVolunteerPensionRate);
        return laborVolunteerPensionFee;
    }

    // (不足月)計算勞退自提費用,按比例計算
    public Integer calculateLaborVolunteerPensionFeeProrated(LocalDate hireDate, Double laborVolunteerPensionRate,
                                                             Integer laborInsuranceGrade) {

        // 取得到職之"日"
        int dd = hireDate.getDayOfMonth();
        System.out.println("到職日" + dd);
        // (30 − 加保日＋1＝該月保費計收日數)，而30日加保或31日加保，保險費均計收1日==>此公式不用再判斷閏年，指判斷當月是30或31天
        // workDays 是int，整数相除会得到整数结果，这意味着小数部分会被截断，转换为浮点数

        Double workDays = (double) (30 - dd + 1);
        System.out.println(workDays);

        if (workDays > 0) {
            Integer laborVolunteerPensionProratedFee = (int) Math
                    .round(laborInsuranceGrade * laborVolunteerPensionRate * (workDays / 30));
            System.out.println(">0" + laborVolunteerPensionProratedFee);
            return laborVolunteerPensionProratedFee;
        } else {
            Integer laborVolunteerPensionProratedFee = (int) Math
                    .round(laborInsuranceGrade * laborVolunteerPensionRate * (workDays + 1) / 30);
            System.out.println("其他:" + laborVolunteerPensionProratedFee);
            return laborVolunteerPensionProratedFee;
        }
    }

    // (足月)福利金
    public Integer calculateWelfareFee(String yn, Integer salary) {
        Double welfareDeduValue = SalaryParamEnum.WELFARE.getValue();

        if (yn.equals("1")) {// 1==>扣福利金
            Integer fee = (int) Math.round(salary * welfareDeduValue);
            return fee;
        } else {
            return 0;
        }
    }

    // (不足月) 福利金
    public Integer calculateWelfareFeeProrated(LocalDate hireDate, String yn, Integer salary) {
        // 取得月份最後一天
        LocalDate lastDay = hireDate.with(TemporalAdjusters.lastDayOfMonth());

        Double welfareDeduValue = SalaryParamEnum.WELFARE.getValue();

        // 計算天數，包含結束那一天+1
        int workDays = (int) hireDate.until(lastDay, ChronoUnit.DAYS) + 1;

        if (yn.equals("1")) {// 1==>扣福利金
            Integer fee = (int) Math.round(salary * welfareDeduValue * workDays / 30);
            return fee;
        } else {
            return 0;
        }
    }

    // 計算不足月薪資
    public Integer calculateSalaryProrated(LocalDate hireDate, Integer basicSalary) {

        Integer dailySalary = basicSalary / 30;
        System.out.println("日薪:" + dailySalary);

        // 取得月份最後一天
        LocalDate lastDay = hireDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("到職月最後一天:" + lastDay);

        // 計算天數，包含結束那一天+1
        int workDays = (int) hireDate.until(lastDay, ChronoUnit.DAYS) + 1;
        System.out.println("實際工作天數:" + workDays);

        Integer actualSalary = dailySalary * workDays;
        return actualSalary;
    }


}
