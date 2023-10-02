package com.eipulse.teamproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.teamproject.entitys.Leave;
import com.eipulse.teamproject.repository.LeaveRepository;

@Transactional
@Service
public class LeaveServiceImpl implements LeaveService {

	private LeaveRepository leaveRepo;
	
	
	@Autowired
	public LeaveServiceImpl(LeaveRepository leaveRepo) {
		this.leaveRepo = leaveRepo;
	}

	@Override
	public Leave saveLeave(Leave leave) {
		return leaveRepo.save(leave);
	}

	@Override
	public List<Leave> findAllLeave() {
		List<Leave> leaveList=leaveRepo.findAll();
		return leaveList;
	}

	@Override
	public Optional<Leave> findLeaveByFormId(Integer formId) {
		if(formId!=null) {
			return leaveRepo.findById(formId);
		}
		return Optional.empty();
	}

	@Override
	public boolean deleteById(Integer formId) {
			leaveRepo.deleteById(formId);
			return true;		
	}
	
}
