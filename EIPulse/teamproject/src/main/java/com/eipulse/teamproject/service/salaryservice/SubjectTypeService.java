package com.eipulse.teamproject.service.salaryservice;

import com.eipulse.teamproject.dto.salarydto.SubjectTypeDto;
import com.eipulse.teamproject.entity.salaryentity.SubjectType;
import com.eipulse.teamproject.repository.salaryrepository.SubjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SubjectTypeService {

    private SubjectTypeRepository subjectRepo;

    @Autowired
    public SubjectTypeService(SubjectTypeRepository subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    // 建立&更新
    public SubjectTypeDto saveOrUpdate(SubjectTypeDto subjectDto) {

        SubjectType subject = new SubjectType(subjectDto);
        SubjectType result = subjectRepo.save(subject);
        String frequencyValue = result.getFrequency();
        String calculateValue = result.getCalculateType();
        String status = result.getStatus();


        Map<String, String> calculateMap = new HashMap<>();
        calculateMap.put("P", "加項");
        calculateMap.put("M", "減項");
        result.setCalculateType(calculateMap.get(calculateValue));

        Map<String, String> frequencyeMap = new HashMap<>();
        frequencyeMap.put("0", "變動");
        frequencyeMap.put("1", "固定");
        result.setFrequency(frequencyeMap.get(frequencyValue));

        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("0", "停止");
        statusMap.put("1", "啟用");
        result.setStatus(statusMap.get(status));

        return new SubjectTypeDto(result);
    }

    // 透過id找科目
    public SubjectTypeDto findById(Integer id) {
        Optional<SubjectType> optional = subjectRepo.findById(id);
        if (optional.isPresent()) {
            SubjectType subject = optional.get();
            return new SubjectTypeDto(subject);
        }
        return null;

    }

    // 找尋全部
    public List<SubjectTypeDto> findAll() {
        List<SubjectType> subjectList = subjectRepo.findAll();
        List<SubjectTypeDto> dtoList = new ArrayList<>();
        for (SubjectType s : subjectList) {
            String calculateType = s.getCalculateType();
            String frequency = s.getFrequency();
            String status = s.getStatus();

            Map<String, String> calculateMap = new HashMap<>();
            calculateMap.put("P", "加項");
            calculateMap.put("M", "減項");
            s.setCalculateType(calculateMap.get(calculateType));

            Map<String, String> frequencyeMap = new HashMap<>();
            frequencyeMap.put("0", "變動");
            frequencyeMap.put("1", "固定");
            s.setFrequency(frequencyeMap.get(frequency));

            Map<String, String> statusMap = new HashMap<>();
            statusMap.put("0", "停止");
            statusMap.put("1", "啟用");
            s.setStatus(statusMap.get(status));

            SubjectTypeDto subjectDto = new SubjectTypeDto(s);
            dtoList.add(subjectDto);
        }
        return dtoList;
    }

    // 名字模糊搜尋
    public List<SubjectTypeDto> findByNameLike(String name) {
        List<SubjectType> subjectList = subjectRepo.findBySubjectNameLike(name);
        List<SubjectTypeDto> dtoList = new ArrayList<>();
        for (SubjectType s : subjectList) {
            SubjectTypeDto subjectDto = new SubjectTypeDto(s);
            dtoList.add(subjectDto);
        }
        return dtoList;
    }

    // 更新狀態(啟用/不啟用)
    public Boolean enabledSubject(Integer id, String newStatus) {
        SubjectType subjectType = subjectRepo.findById(id).get();
        String oldStatus = subjectType.getStatus();
        if (oldStatus.equals(newStatus)) {
            subjectType.setStatus(newStatus);
            subjectRepo.save(subjectType);
            return true;
        } else {
            return false;
        }

    }

    // 找科目啟用
    public List<SubjectTypeDto> findSubjectIsOpen() {
        List<SubjectType> result = subjectRepo.findByStatus();
        List<SubjectTypeDto> dtoList = new ArrayList<>();
        for (SubjectType s : result) {
            String calculateType = s.getCalculateType();
            String frequency = s.getFrequency();
            String status = s.getStatus();

            Map<String, String> calculateMap = new HashMap<>();
            calculateMap.put("P", "加項");
            calculateMap.put("M", "減項");
            s.setCalculateType(calculateMap.get(calculateType));

            Map<String, String> frequencyeMap = new HashMap<>();
            frequencyeMap.put("0", "變動");
            frequencyeMap.put("1", "固定");
            s.setFrequency(frequencyeMap.get(frequency));

            Map<String, String> statusMap = new HashMap<>();
            statusMap.put("0", "停止");
            statusMap.put("1", "啟用");
            s.setStatus(statusMap.get(status));

            SubjectTypeDto subjectDto = new SubjectTypeDto(s);
            dtoList.add(subjectDto);
        }
        return dtoList;
    }

    //  找加項且啟用的科目
    public List<SubjectTypeDto> findPlus() {
        List<SubjectType> result = subjectRepo.findTypeIsP();
        List<SubjectTypeDto> dtoList = new ArrayList<>();
        for (SubjectType s : result) {
            String calculateType = s.getCalculateType();
            String frequency = s.getFrequency();
            String status = s.getStatus();

            Map<String, String> calculateMap = new HashMap<>();
            calculateMap.put("P", "加項");
            calculateMap.put("M", "減項");
            s.setCalculateType(calculateMap.get(calculateType));

            Map<String, String> frequencyeMap = new HashMap<>();
            frequencyeMap.put("0", "變動");
            frequencyeMap.put("1", "固定");
            s.setFrequency(frequencyeMap.get(frequency));

            Map<String, String> statusMap = new HashMap<>();
            statusMap.put("0", "停止");
            statusMap.put("1", "啟用");
            s.setStatus(statusMap.get(status));

            SubjectTypeDto subjectDto = new SubjectTypeDto(s);
            dtoList.add(subjectDto);
        }
        return dtoList;
    }

    //  找減項且啟用的科目
    public List<SubjectTypeDto> findMinus() {
        List<SubjectType> result = subjectRepo.findTypeIsM();
        List<SubjectTypeDto> dtoList = new ArrayList<>();
        for (SubjectType s : result) {
            String calculateType = s.getCalculateType();
            String frequency = s.getFrequency();
            String status = s.getStatus();

            Map<String, String> calculateMap = new HashMap<>();
            calculateMap.put("P", "加項");
            calculateMap.put("M", "減項");
            s.setCalculateType(calculateMap.get(calculateType));

            Map<String, String> frequencyeMap = new HashMap<>();
            frequencyeMap.put("0", "變動");
            frequencyeMap.put("1", "固定");
            s.setFrequency(frequencyeMap.get(frequency));

            Map<String, String> statusMap = new HashMap<>();
            statusMap.put("0", "停止");
            statusMap.put("1", "啟用");
            s.setStatus(statusMap.get(status));

            SubjectTypeDto subjectDto = new SubjectTypeDto(s);
            dtoList.add(subjectDto);
        }
        return dtoList;
    }
}

