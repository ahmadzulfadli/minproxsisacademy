package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menurole/")
public class MMenuRoleController {
	
	@GetMapping("indexapi")
	public String index() {
		return "menurole/indexapi.html";
	}

}
