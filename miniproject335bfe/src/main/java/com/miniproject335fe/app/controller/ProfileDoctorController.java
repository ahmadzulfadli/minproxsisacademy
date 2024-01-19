package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile/")
public class ProfileDoctorController {

	@GetMapping("index")
	public String index() {
		return "profile/profileDoctor.html";
	}
}
