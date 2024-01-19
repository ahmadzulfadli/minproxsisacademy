package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/payment/")
public class MPaymentMethodController {
    
    @GetMapping("read")
    public String index(){
        return "paymentmethod/index.html";
    }
}
