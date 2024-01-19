package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dokter/")
public class SpecializationController {
	@GetMapping("specialization")
	public String spesialis() {
		return "dokter/spesialis.html";
	}

}
