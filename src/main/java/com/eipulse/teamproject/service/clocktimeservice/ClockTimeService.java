package com.eipulse.teamproject.service.clocktimeservice;

import com.eipulse.teamproject.dto.clocktimedto.ClockTimeDTO;
import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import com.eipulse.teamproject.entity.clocktimeentity.ClockTime;
import com.eipulse.teamproject.entity.Employee;
import com.eipulse.teamproject.entity.clocktimeentity.OfficeRegions;
import com.eipulse.teamproject.repository.clocktimerepository.AttendanceRepository;
import com.eipulse.teamproject.repository.clocktimerepository.ClockTimeRepository;
import com.eipulse.teamproject.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClockTimeService {
    private ClockTimeRepository clockTimeRepository;
    private EmpRepository empRepository;
    private AttendanceRepository attendanceRepository;
    private OfficeRegionsService officeRegionsService;

    @Autowired
    public ClockTimeService(ClockTimeRepository clockTimeRepository, EmpRepository empRepository, AttendanceRepository attendanceRepository, OfficeRegionsService officeRegionsService) {
        this.clockTimeRepository = clockTimeRepository;
        this.empRepository = empRepository;
        this.attendanceRepository = attendanceRepository;
        this.officeRegionsService = officeRegionsService;
    }


    public ClockTime saveClockTime(ClockTimeDTO clockTimeDTO) {
//        Search emp near company
        OfficeRegions officeRegions = officeRegionsService.findByLikeRegionsDistance(clockTimeDTO.getLatitude(),clockTimeDTO.getLongitude());
        Employee employee = empRepository.findById(clockTimeDTO.getEmpId()).orElseThrow(()->new RuntimeException("員工資料異常"));
        LocalDateTime now = LocalDateTime.now();
        ClockTime clockTime = new ClockTime(now, clockTimeDTO.getType(), employee);

        ClockTimeDTO lastClockTime = findByEmpIdToDayLastTime(clockTime.getEmployee().getEmpId());

        if (lastClockTime != null) {
            if ("上班".equals(lastClockTime.getType())) {
                clockTime.setType("下班");
            } else {
                clockTime.setType("上班");
            }
        } else {
            clockTime.setType("上班");
        }

        LocalDate today = clockTime.getTime().toLocalDate();
//        calculate emp with company distance
        double empLocation = officeRegionsService.haversineDistance(officeRegions.getLatitude(),officeRegions.getLongitude(),clockTimeDTO.getLatitude(),clockTimeDTO.getLongitude());
        System.out.println(empLocation);
        if(empLocation <= 200){
            clockTime.setOfficeRegions(officeRegions);
            Optional<Attendance> optional = attendanceRepository.findByDateAndEmployee(today, clockTime.getEmployee());
            if(optional.isEmpty()){
//                create new attendance
                Attendance newAttendance = new Attendance();
                newAttendance.setDate(today);
                newAttendance.setEmployee(clockTime.getEmployee());
                newAttendance.setTotalHours(BigDecimal.ZERO);
                attendanceRepository.save(newAttendance);
            }
            return clockTimeRepository.save(clockTime);
        }
        throw new RuntimeException("打卡異常");
    }

    //抓取單員工當日是否有打卡記錄，如有則回傳最後一筆，沒有則不顯示，用於主頁顯示 //待修改成前端輸入日期只單拉當天紀錄，否則有性能疑慮

    public ClockTimeDTO findByEmpIdToDayLastTime(Integer empId) {
        List<ClockTime> lastTime = clockTimeRepository.findByEmpIdLastTime(empId);
        LocalDate toDay = LocalDate.now();
        LocalDate lastTimeDay ;
        if(lastTime!=null && !lastTime.isEmpty()){
            lastTimeDay = lastTime.get(0).getTime().toLocalDate();
            if(lastTimeDay.equals(toDay)){
                return new ClockTimeDTO(lastTime.get(0));
            }
        }
        return null;
    }

    //選擇要查詢日期的單員工的打卡記錄
    public Page<ClockTimeDTO>findClockTimeByEmpByDate(Integer empId,LocalDate startDate, LocalDate endDate,Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,10,Sort.Direction.ASC,"time");
        Page<ClockTime> clockTimePage = clockTimeRepository.findByTimeBetweenAndEmployee(empId,startDate,endDate,pageable);
        Page<ClockTimeDTO> dtoPage = clockTimePage.map(clocktime -> new ClockTimeDTO(clocktime));

        return dtoPage;
    }


    //選擇要查詢日期的所有員工的打卡記錄
    public Page<ClockTimeDTO>findClockTimeByDate(LocalDate startDate,LocalDate endDate,Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,10,Sort.Direction.ASC,"time");
        Page<ClockTime> clockTimePage = clockTimeRepository.findClockTimesByDate(startDate,endDate,pageable);
        List<ClockTimeDTO>result = new ArrayList<>();
        for (ClockTime clocktime:clockTimePage.getContent()) {
            result.add(new ClockTimeDTO(clocktime));
        }
        return new PageImpl<>(result,pageable,clockTimePage.getTotalElements());
    }

}
