package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/courier/")
public class MCourierController {
    
    @GetMapping("read")
    public String index() {
        return "courier/index.html";
    }
}
