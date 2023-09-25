package com.eipulse.teamproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/maps")
    public String mpasPage(){
        return "/googleMaps/locatorplus";
    }

    @GetMapping("/forgetPassword")
    public String forgetPage(){
        return "/login/forgetPassword";
    }
}
