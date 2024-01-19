package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/doctor/")
public class DoctorDetail {
    
    @GetMapping("detail")
    public String index(){
        return "doctordetails/index.html";
    }
}
