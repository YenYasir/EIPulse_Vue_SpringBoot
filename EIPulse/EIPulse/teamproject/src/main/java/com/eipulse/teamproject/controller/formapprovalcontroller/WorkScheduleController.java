package com.eipulse.teamproject.controller.formapprovalcontroller;
import com.eipulse.teamproject.entity.formapproval.WorkSchedule;
import com.eipulse.teamproject.service.formapprovalservice.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/workschedules")
public class WorkScheduleController {

    private WorkScheduleService workScheduleService;


    @Autowired
    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    // 新增新的工作時間表
    @PostMapping
    public boolean createWorkSchedule(@RequestBody WorkSchedule workSchedule) {
        return workScheduleService.createWorkSchedule(workSchedule);
    }

    // 找特定員工的工作時間表
    @GetMapping("/{employeeId}")
    public List<WorkSchedule> getWorkSchedulesByEmployee(@PathVariable int employeeId) {
        return workScheduleService.getWorkSchedulesByEmployee(employeeId);
    }

    // 更新工作時間表
    @PutMapping("/{scheduleId}")
    public WorkSchedule updateWorkSchedule(@PathVariable int scheduleId, @RequestBody WorkSchedule workSchedule) {
        return workScheduleService.updateWorkSchedule(scheduleId, workSchedule);
    }

    // 删除工作時間表
    @DeleteMapping("/{scheduleId}")
    public void deleteWorkSchedule(@PathVariable int scheduleId) {
        workScheduleService.deleteWorkSchedule(scheduleId);
    }
}
