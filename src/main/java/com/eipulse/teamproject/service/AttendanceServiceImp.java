package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Attendance;
import com.eipulse.teamproject.entitys.ClockTime;
import com.eipulse.teamproject.entitys.Employee;
import com.eipulse.teamproject.repository.AttendanceRepository;
import com.eipulse.teamproject.repository.ClockTimeRepository;
import com.eipulse.teamproject.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class AttendanceServiceImp implements AttendanceService {
    private AttendanceRepository attendanceRepository;
    private ClockTimeRepository clockTimeRepository;
    private EmpRepository empRepository;

    @Autowired
    public AttendanceServiceImp(AttendanceRepository attendanceRepository, ClockTimeRepository clockTimeRepository,EmpRepository empRepository) {
        this.attendanceRepository = attendanceRepository;
        this.clockTimeRepository = clockTimeRepository;
        this.empRepository = empRepository;
    }


//    9/24 計算當日時數OK，待優化
    @Override
    public Attendance updateAttendance(Integer empId,LocalDateTime startTime, LocalDateTime endTime) {
        Employee employee = empRepository.findById(empId).orElse(null);
        List<ClockTime> clockTimes = clockTimeRepository.findByTimeAndEmployee(employee,startTime,endTime);
        clockTimes.sort(Comparator.comparing(ClockTime::getTime));
        BigDecimal totalHours = BigDecimal.ZERO;
        LocalDateTime lastTime = null;
        for (ClockTime clockTime : clockTimes) {
            if (lastTime != null) {
                Duration duration = Duration.between(lastTime, clockTime.getTime());
                long minutes = duration.toMinutes();
                BigDecimal minutesBigDecimal = BigDecimal.valueOf(minutes);
                BigDecimal minutesChangeHour = BigDecimal.valueOf(60);
                BigDecimal hoursBigDecimal = minutesBigDecimal.divide(minutesChangeHour, 2, RoundingMode.HALF_UP);
                totalHours = totalHours.add(hoursBigDecimal);
            }
            lastTime = clockTime.getTime();
        }
        LocalDate time = startTime.toLocalDate();
        Optional<Attendance> optional = attendanceRepository.findByDateAndEmployee(time, employee);
        if (optional.isPresent()) {
            Attendance attendance = optional.get();
            attendance.setTotalHours(totalHours);
            if (totalHours.compareTo(BigDecimal.valueOf(8)) >= 0 ) {
                attendance.setStatus("已滿所需時數");

                 attendanceRepository.save(attendance);
            } else if (totalHours.compareTo(BigDecimal.valueOf(8)) < 0) {
                attendance.setStatus("出勤時數未滿");
                attendanceRepository.save(attendance);
                 attendanceRepository.save(attendance);
            }
            return attendanceRepository.save(attendance);
        }
        return null;
    }

    @Override
    public Attendance findByEmpIdLastAttendance(Integer empId) {
        return null;
    }

    @Override
    public List<Attendance> findByEmpIdAllAttendance(Integer empId) {
        return null;
    }

    @Override
    public List<Attendance> findAllEmpAttendance() {

        return null;
    }
}
