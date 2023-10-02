package com.eipulse.teamproject.service;

import java.util.List;
import java.util.Optional;

import com.eipulse.teamproject.entitys.Leave;


public interface LeaveService {
    Leave saveLeave(Leave leave);
    List<Leave> findAllLeave();
    Optional<Leave> findLeaveByFormId(Integer formId);
    boolean deleteById(Integer formId);
}
