package com.eipulse.teamproject.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eipulse.teamproject.dto.OvertimeDTO;
import com.eipulse.teamproject.entitys.Overtime;
import com.eipulse.teamproject.entitys.OvertimeType;
import com.eipulse.teamproject.repository.OvertimeRepository;

@Transactional
@Service
public class OvertimeService{

	private OvertimeRepository overRepo;
	
	@Autowired
	public OvertimeService(OvertimeRepository overRepo) {
		this.overRepo = overRepo;
	}
	
	
	// 新增加班單或修改加班單
	public OvertimeDTO createAndUpdateOvertime(OvertimeDTO overtimeDTO) {
		Overtime over = new Overtime();
	    OvertimeType overtimeType = new OvertimeType();
	    // 先做轉型
	    overtimeType.setId(overtimeDTO.getTypeId());
	    // 成功存入 OvertimeType 型別
	    over.setType(overtimeType);
	    over.setFormId(overtimeDTO.getFormId());
	    over.setReason(overtimeDTO.getReason());
	    over.setDate(overtimeDTO.getDate());
	    over.setStartTime(overtimeDTO.getStartTime());
	    over.setEndTime(overtimeDTO.getEndTime());
	    over.setFile(overtimeDTO.getFile());
	    
	    over=overRepo.save(over);
	    return new OvertimeDTO(over);
	  }
	
	// 查詢全部
	public List<OvertimeDTO> findAllOvertimes() {

		  List<Overtime> overs = overRepo.findAll();
		  List<OvertimeDTO> overDTOs = new ArrayList<>();
		  
		  // 使用 for 迴圈把 overs 逐一存入 over
		  for (Overtime over : overs) {
			// 將 over 轉成 overtimeDTO
		    OvertimeDTO overtimeDTO = new OvertimeDTO(over); 
		    overDTOs.add(overtimeDTO);
		  }
		  return overDTOs;
		}

	// 透過表單編號查詢
	public OvertimeDTO findOvertimeByFormId(Integer formId) {
	    if (formId != null) {
	        return new OvertimeDTO(overRepo.findByFormId(formId));
	    } else {
	        return null; 
	    }
	}
	
	// 透過表單類型查詢
	public List<OvertimeDTO> findOvertimesByType(Integer leaveType) {

		  List<Overtime> overs = overRepo.findByOvertimeType(leaveType);

		  if (overs.isEmpty()) {
		    System.out.println("找不到相應的記錄");
		    return null;
		  }
		  List<OvertimeDTO> overDTOs = new ArrayList<>();
		  for (Overtime over : overs) {
				// 將 over 轉成 overtimeDTO
			    OvertimeDTO overtimeDTO = new OvertimeDTO(over); 
			    overDTOs.add(overtimeDTO);
			  }
			  return overDTOs;
		}
	
	// 透過表單編號刪除
	public void deleteByFormId(Integer formId) {
		overRepo.deleteByFormId(formId);	
	}

		
}
