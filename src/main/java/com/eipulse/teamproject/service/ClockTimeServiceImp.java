//package com.eipulse.teamproject.service;
//
//import com.eipulse.teamproject.entitys.ClockTime;
//import com.eipulse.teamproject.repository.ClockTimeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Transactional
//@Service
//public class ClockTimeServiceImp implements ClockTimeService {
//    private ClockTimeRepository clockTimeRepository;
//
//    @Autowired
//    public ClockTimeServiceImp(ClockTimeRepository clockTimeRepository) {
//        this.clockTimeRepository = clockTimeRepository;
//    }
//    @Override
//    public ClockTime saveClockTime(ClockTime clockTime){
//        if (clockTime != null) {
//            return clockTimeRepository.save(clockTime);
//        }
//        return null;
//    }
//    @Override
//    public List<ClockTime>findAllEmpTime(){
//        List<ClockTime> clockTimeList =  clockTimeRepository.findAll();
//        return clockTimeList;
//    }
//
//    @Override
//    public List<ClockTime> findAllByIdTime(Integer empId) {
//        if(empId != null){
//            return clockTimeRepository.findAllByIdTime(empId);
//        }
//        return null;
//    }
//    @Override
//    public Void deleteClockTime(Integer clockTimeId){
//        Optional<ClockTime> optional = clockTimeRepository.findById(clockTimeId);
//
//        if(optional.isPresent()){
//            clockTimeRepository.deleteById(clockTimeId);
//        }
//        return null;
//    }
//}
