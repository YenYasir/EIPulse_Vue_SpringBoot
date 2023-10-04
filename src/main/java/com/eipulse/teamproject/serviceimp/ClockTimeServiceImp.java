package com.eipulse.teamproject.serviceimp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eipulse.teamproject.dto.ClockTimeDTO;
import com.eipulse.teamproject.entitys.Attendance;
import com.eipulse.teamproject.entitys.ClockTime;
import com.eipulse.teamproject.entitys.Employee;
import com.eipulse.teamproject.entitys.OfficeRegions;
import com.eipulse.teamproject.repository.AttendanceRepository;
import com.eipulse.teamproject.repository.ClockTimeRepository;
import com.eipulse.teamproject.repository.EmpRepository;
import com.eipulse.teamproject.service.ClockTimeService;

@Service
public class ClockTimeServiceImp implements ClockTimeService {
	private ClockTimeRepository clockTimeRepository;
	private EmpRepository empRepository;
	private AttendanceRepository attendanceRepository;
	private OfficeRegionsServiceImp officeRegionsServiceImp;

	@Autowired
	public ClockTimeServiceImp(ClockTimeRepository clockTimeRepository, EmpRepository empRepository,
			AttendanceRepository attendanceRepository, OfficeRegionsServiceImp officeRegionsServiceImp) {
		this.clockTimeRepository = clockTimeRepository;
		this.empRepository = empRepository;
		this.attendanceRepository = attendanceRepository;
		this.officeRegionsServiceImp = officeRegionsServiceImp;
	}

	@Override
	public ClockTime saveClockTime(Integer empId, String type, double latitude, double longitude) {
//        Search emp near company
		OfficeRegions officeRegions = officeRegionsServiceImp.findByLikeRegionsDistance(latitude, longitude);
		Employee employee = empRepository.findById(empId).orElse(null);
		LocalDateTime now = LocalDateTime.now();
		ClockTime clockTime = new ClockTime(now, type, employee);
		LocalDate today = clockTime.getTime().toLocalDate();
//        calculate emp with company distance
		double empLocation = officeRegionsServiceImp.haversineDistance(officeRegions.getLatitude(),
				officeRegions.getLongitude(), latitude, longitude);
		System.out.println(empLocation);
		if (empLocation <= 200) {
			clockTime.setOfficeRegions(officeRegions);
			Optional<Attendance> optional = attendanceRepository.findByDateAndEmployee(today, clockTime.getEmployee());
			if (optional.isEmpty()) {
//                create new attendance
				Attendance newAttendance = new Attendance();
				newAttendance.setDate(today);
				newAttendance.setEmployee(clockTime.getEmployee());
				newAttendance.setTotalHours(BigDecimal.ZERO);
				newAttendance.setStatus("未完成");
				attendanceRepository.save(newAttendance);
			}
			return clockTimeRepository.save(clockTime);
		}
		return null;
	}

	@Override
	public ClockTimeDTO findByEmpIdLastTime(Integer empId) {
		List<ClockTime> lastTime = clockTimeRepository.findByEmpIdLastTime(empId);
		List<ClockTimeDTO> resultList = new ArrayList<>();
		if (lastTime != null) {
			for (ClockTime clocktime : lastTime) {
				resultList.add(new ClockTimeDTO(clocktime));
			}
			return resultList.get(0);
		}
		return null;
	}

	@Override
	public List<ClockTimeDTO> findByEmpIdAllTime(Integer empId) {
		List<ClockTime> clockTimeList = clockTimeRepository.findAllByIdTime(empId);
		List<ClockTimeDTO> resultList = new ArrayList<>();
		if (clockTimeList != null) {
			for (ClockTime clockTime : clockTimeList) {
				resultList.add(new ClockTimeDTO(clockTime));
			}
			return resultList;
		}
		return null;
	}

	@Override
	public List<ClockTimeDTO> findAllTime() {
		List<ClockTime> clockTimeList = clockTimeRepository.findAll();
		List<ClockTimeDTO> resultList = new ArrayList<>();
		if (clockTimeList != null) {
			for (ClockTime clockTime : clockTimeList) {
				resultList.add(new ClockTimeDTO(clockTime));
			}
			return resultList;
		}
		return null;
	}
}
