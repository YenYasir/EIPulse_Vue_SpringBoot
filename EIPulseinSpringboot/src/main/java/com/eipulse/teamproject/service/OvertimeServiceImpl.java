package com.eipulse.teamproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eipulse.teamproject.entitys.Leave;
import com.eipulse.teamproject.entitys.Overtime;
import com.eipulse.teamproject.repository.LeaveRepository;
import com.eipulse.teamproject.repository.OvertimeRepository;

@Service
public class OvertimeServiceImpl implements OvertimeService {
	
	private OvertimeRepository overtimeRepo;
	
	
	@Autowired
	public OvertimeServiceImpl(OvertimeRepository overtimeRepo) {
		this.overtimeRepo = overtimeRepo;
	}

	@Override
	public Overtime saveOvertime(Overtime overtime) {	
		return overtimeRepo.save(overtime);
	}

	@Override
	public List<Overtime> findAllOvertime() {	
		List<Overtime> overtimeList=overtimeRepo.findAll();
		return overtimeList;
	}

	@Override
	public Optional<Overtime> findByFormId(Integer formId) {
		if(formId!=null) {
			return overtimeRepo.findById(formId);
		}
		return Optional.empty();
	}

	@Override
	public void deleteById(Integer formId) {
		if(formId!=null) {
			overtimeRepo.deleteById(formId);
		}

	}

}
