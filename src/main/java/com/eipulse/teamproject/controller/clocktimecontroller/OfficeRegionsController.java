package com.eipulse.teamproject.controller.clocktimecontroller;


import com.eipulse.teamproject.entity.clocktimeentity.OfficeRegions;
import com.eipulse.teamproject.service.clocktimeservice.OfficeRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OfficeRegionsController {

    private OfficeRegionsService officeRegionsService;

    @Autowired
    public OfficeRegionsController(OfficeRegionsService officeRegionsService) {
        this.officeRegionsService = officeRegionsService;
    }
//OK
    @PostMapping("/officeRegions/post")
    public OfficeRegions postOfficeRegions(@RequestBody OfficeRegions officeRegions){

        return officeRegionsService.saveRegions(officeRegions);
    }
//    ok
    @GetMapping("/officeRegions/all")
    public List<OfficeRegions> findAllOfficeRegions (){
        return officeRegionsService.findAllRegions();
    }

}
