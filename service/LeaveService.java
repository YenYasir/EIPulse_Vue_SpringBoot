package com.eipulse.teamproject.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eipulse.teamproject.dto.LeaveDTO;
import com.eipulse.teamproject.entitys.Leave;
import com.eipulse.teamproject.entitys.LeaveType;
import com.eipulse.teamproject.repository.LeaveRepository;

@Transactional
@Service
public class LeaveService{

	private LeaveRepository leaveRepo;
	
	@Autowired
	public LeaveService(LeaveRepository leaveRepo) {
		this.leaveRepo = leaveRepo;
	}
	
	// 新增或修改表單功能，於此處先將 leaveType 的 Id 提取出來存進 leave 物件，再將 leave 物件轉成 dto 儲存
	public LeaveDTO createAndUpdateLeave(LeaveDTO leaveDTO) {
	    Leave leave = new Leave();
	    LeaveType leaveType = new LeaveType();
	    // 先做轉型
	    leaveType.setTypeId(leaveDTO.getTypeId());
	    // 成功存入LeaveType型別
	    leave.setLeaveType(leaveType);
	    leave.setFormId(leaveDTO.getFormId());
	    leave.setReason(leaveDTO.getReason());
	    leave.setDays(leaveDTO.getDays());
	    leave.setHours(leaveDTO.getHours());
	    leave.setStartTime(leaveDTO.getStartTime());
	    leave.setFile(leaveDTO.getFile());
	    
	    leave = leaveRepo.save(leave);
	    
	    return new LeaveDTO(leave);
	  }
//	
//	public LeaveDto convertToDto(Leave leave)  {
//		 LeaveDto dto=new LeaveDto();
//		 dto.setTypeId(leave.getLeaveType().getTypeId());
//		 dto.setReason(leave.getReason());
//		 dto.setDays(leave.getDays());
//		 dto.setHours(leave.getHours());
//		 dto.setStartTime(leave.getStartTime());
//		 dto.setFile(leave.getFile());		 
//		 return dto;
//
//	}
	
//	public LeaveDto insertLeave(LeaveDto leaveDto) {
//
//		  Leave leave = new Leave();
//		  
//		  leave.setLeaveType(new LeaveType());
//		  leave.setReason(leaveDto.getReason());
//		  leave.setDays(leaveDto.getDays());
//		  leave.setHours(leaveDto.getHours());
//		  leave.setStartTime(leaveDto.getStartTime());
//		  leave.setFile(leaveDto.getFile());
//		  
//		  leave = leaveRepo.save(leave);
//		  
//		  return new LeaveDto(leave);
//		}
	
	
	// 找尋所有請假單
	public List<LeaveDTO> findAllLeaves() {
		
		  List<Leave> leaves = leaveRepo.findAll();
		  List<LeaveDTO> leaveDtos = new ArrayList<>();
		  
		  // 使用for迴圈存入leave
		  for (Leave leave : leaves) {
		    LeaveDTO leaveDto = new LeaveDTO(leave); 
		    leaveDtos.add(leaveDto);
		  }
		  return leaveDtos;
		}

	// 透過表單編號查詢，直接返回一個單一的物件（可能為Leave或null）
	public LeaveDTO findLeaveByFormId(Integer formId) {
	    if (formId != null) {
	        return new LeaveDTO(leaveRepo.findByFormId(formId));
	    } else {
	        return null; 
	    }
	}
	
	// 透過表單類型查詢
	public List<LeaveDTO> findLeavesByLeaveType(Integer leaveType) {

		  List<Leave> leaves = leaveRepo.findLeaveByTypeId(leaveType);
		  if (leaves.isEmpty()) {
		    System.out.println("找不到相應的記錄");
		    return null;
		  }
		  List<LeaveDTO> leaveDtos = new ArrayList<>();

		  for (Leave leave : leaves) {
		    LeaveDTO leaveDto = new LeaveDTO(leave); 
		    leaveDtos.add(leaveDto);
		  }
		  return leaveDtos;
		}
//	 public List<LeaveDto> findLeavesByLeaveType(Integer leaveType) {
//		 
//	        List<Leave> leaves = leaveRepo.findByLeaveType(leaveType);
//	        
//	        if (leaves.isEmpty()) {
//	            System.out.println("找不到相應的記錄");
//	        }	        
//	        return leaves;
//	    }
	
	// 透過表單編號刪除請假單
	public void deleteByFormId(Integer formId) {
			leaveRepo.deleteByFormId(formId);	
	}
	
//	public LeaveDto updateLeave(LeaveDto leaveDto) {
//	    Leave leave = leaveRepo.findByFormId(leaveDto.getFormId());
//	    if (leave != null) {
//	      leave.setLeaveType(leaveDto.getTypeId());
//	      leave.setReason(leaveDto.getReason());
//	      leave.setDays(leaveDto.getDays());
//	      leave.setHours(leaveDto.getHours());
//	      leave.setStartTime(leaveDto.getStartTime());
//	      leave.setFile(leaveDto.getFile());
//
//	      leave = leaveRepo.save(leave);
//	      return new LeaveDto(leave);
//	    }
//	    return null;
//	  }

		
}
