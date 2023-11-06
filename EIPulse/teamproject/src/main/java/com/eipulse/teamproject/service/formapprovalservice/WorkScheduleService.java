package com.eipulse.teamproject.service.formapprovalservice;
import com.eipulse.teamproject.entity.formapproval.WorkSchedule;
import com.eipulse.teamproject.repository.formapprovalrepository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkScheduleService {

    private WorkScheduleRepository workScheduleRepository;


    @Autowired
    public WorkScheduleService(WorkScheduleRepository workScheduleRepository) {
        this.workScheduleRepository = workScheduleRepository;
    }



    public boolean createWorkSchedule(WorkSchedule workSchedule) {
        if(workScheduleRepository.findRepetition(workSchedule.getEmpId(),workSchedule.getWeekday())){
            return false;
        }
        workScheduleRepository.save(workSchedule);
        return true;
    }

    public List<WorkSchedule> getWorkSchedulesByEmployee(int employeeId) {
        return workScheduleRepository.findByEmployeeID(employeeId);
    }

    public WorkSchedule updateWorkSchedule(int scheduleId, WorkSchedule workSchedule) {
        WorkSchedule existingSchedule = workScheduleRepository.findById(scheduleId).orElse(null);
        if (existingSchedule != null) {
            existingSchedule.setWeekday(workSchedule.getWeekday());
            existingSchedule.setStartTime(workSchedule.getStartTime());
            existingSchedule.setEndTime(workSchedule.getEndTime());
            existingSchedule.setLunchStart(workSchedule.getLunchStart());
            existingSchedule.setLunchEnd(workSchedule.getLunchEnd());
            return workScheduleRepository.save(existingSchedule);
        }
        return null;
    }

    public void deleteWorkSchedule(int scheduleId) {
        workScheduleRepository.deleteById(scheduleId);
    }
}
