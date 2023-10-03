package com.eipulse.teamproject.service;

import java.util.List;

import com.eipulse.teamproject.entitys.ClockTime;

public interface ClockTimeService {
	ClockTime saveClockTime(ClockTime clockTime);

	List<ClockTime> findAllEmpTime();

	List<ClockTime> findAllByIdTime(Integer empId);

	Void deleteClockTime(Integer clockTimeId);
}
