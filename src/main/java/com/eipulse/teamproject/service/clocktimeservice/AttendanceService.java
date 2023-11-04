package com.eipulse.teamproject.service.clocktimeservice;

import com.eipulse.teamproject.dto.clocktimedto.AttendanceDTO;
import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import com.eipulse.teamproject.entity.clocktimeentity.ClockTime;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.repository.clocktimerepository.AttendanceRepository;
import com.eipulse.teamproject.repository.clocktimerepository.ClockTimeRepository;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

//遲到、早退、曠班、請假、正常、超時
@Service
public class AttendanceService {
    private LocalDateTime startOfDay() {
        return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime endOfDay() {
        return LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0);
    }
    private AttendanceRepository attendanceRepository;
    private ClockTimeRepository clockTimeRepository;
    private EmployeeRepository empRepository;
    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, ClockTimeRepository clockTimeRepository, EmployeeRepository empRepository) {
        this.attendanceRepository = attendanceRepository;
        this.clockTimeRepository = clockTimeRepository;
        this.empRepository = empRepository;
    }



    //秒 分 時 每月的日期 每月的月份 星期幾 此設定為每天23:00將執行
    @Scheduled(cron = "0 12 20 * * *")
    //自動更新出勤狀態
    public boolean updateAttendance() {
        List<Employee> employees = empRepository.findAll();
        //先抓取全員工資料後迭代
        for (Employee employee : employees) {
            //抓取每天的00:00 ~ 23:59打卡時間
            LocalDateTime startTime = startOfDay();
            LocalDateTime endTime = endOfDay();

            List<ClockTime> clockTimes = clockTimeRepository.findByTimeAndEmployee(employee, startTime, endTime);
            //抓取單員工所有打卡紀錄後迭代計算總時數
            BigDecimal totalHours = calculateTotalHours(clockTimes);

            //依日期先去抓取當天的出勤紀錄並更新資料
            LocalDate time = startTime.toLocalDate();
            Optional<Attendance> optionalAttendance = attendanceRepository.findByDateAndEmployee(time, employee);
            if (optionalAttendance.isPresent()) {
                if(! clockTimes.isEmpty()){
                    Attendance attendance = optionalAttendance.get();
                    //獲取第一筆上班判斷遲到
                    LocalDateTime firstClockTime = clockTimes.get(0).getTime();
                    //獲取最後一筆判斷是否早退
                    LocalDateTime lastClockTime = clockTimes.get(clockTimes.size() - 1).getTime();
                    attendance.setTotalHours(totalHours);
                    //判斷出勤狀態碼
                    updateAttendanceStatus(attendance, firstClockTime, lastClockTime, totalHours);
                    attendanceRepository.save(attendance);
                }
            }
        }
        return true;
    }

    //計算總時數
    private BigDecimal calculateTotalHours(List<ClockTime> clockTimes) {
        //初始化總時數
        BigDecimal totalHours = BigDecimal.ZERO;
        //預設開始時間 null
        LocalDateTime startTime = null;
        //將單員工當天所有打卡記錄迭代計算
        for (ClockTime clockTime : clockTimes) {
            if ("上班".equals(clockTime.getType())) {
                startTime = clockTime.getTime();
            } else if ("下班".equals(clockTime.getType()) && startTime != null) {
                Duration duration = Duration.between(startTime, clockTime.getTime());
                long minutes = duration.toMinutes();
                BigDecimal minutesBigDecimal = BigDecimal.valueOf(minutes);
                BigDecimal minutesChangeHour = BigDecimal.valueOf(60);
                BigDecimal hoursBigDecimal = minutesBigDecimal.divide(minutesChangeHour, 2, RoundingMode.HALF_UP);
                totalHours = totalHours.add(hoursBigDecimal);
                startTime = null;  // reset startTime for the next pair
            }
        }
        return totalHours;
    }


    //設定出勤狀態碼
    private void updateAttendanceStatus(Attendance attendance, LocalDateTime firstClockTime, LocalDateTime lastClockTime, BigDecimal totalHours) {
        if (firstClockTime.toLocalTime().isAfter(LocalTime.of(8, 0))) {
            attendance.setIsLate(true);
        }
        if (lastClockTime.toLocalTime().isBefore(LocalTime.of(17, 0))) {
            attendance.setIsEarlyLeave(true);
        }
        if (totalHours.compareTo(BigDecimal.valueOf(8)) < 0) {
            attendance.setIsHoursNotMet(true);
        }
        if (totalHours.compareTo(BigDecimal.valueOf(10)) >= 0) {
            attendance.setIsOverTime(true);
        }
    }


    //依區間抓取單員工出勤狀況，最大為單月
    public Page<AttendanceDTO> findByDateBetweenEmpAttendance(LocalDate startDate, LocalDate endDate , Integer empId,Integer pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,8, Sort.Direction.ASC,"date");
        Page<Attendance> attendancePage = attendanceRepository.findByDateBetweenEmpAttendance(startDate, endDate,empId,pageable);
        Page<AttendanceDTO>result = attendancePage.map(attendance -> new AttendanceDTO(attendance));
        return result;
    }

    //依區間抓取全員工出勤狀況，最大為單月
    public Page<AttendanceDTO>findByMonthAllEmpAttendance(LocalDate startDate, LocalDate endDate,Integer pageNumber ){
        Pageable pageable = PageRequest.of(pageNumber-1,8, Sort.Direction.ASC,"date");
        Page<Attendance> attendancePage = attendanceRepository.findByDateBetweenAllEmpAttendance(startDate, endDate,pageable);
        Page<AttendanceDTO> result = attendancePage.map(attendance -> new AttendanceDTO(attendance));

        return result;
    }
}
