package com.eipulse.teamproject.service;

import java.util.List;
import java.util.Optional;

import com.eipulse.teamproject.entitys.Leave;
import com.eipulse.teamproject.entitys.Overtime;


public interface OvertimeService {
    Overtime saveOvertime(Overtime overtime);
    List<Overtime> findAllOvertime();
    Optional<Overtime> findByFormId(Integer formId);
    void deleteById(Integer formId);
}
