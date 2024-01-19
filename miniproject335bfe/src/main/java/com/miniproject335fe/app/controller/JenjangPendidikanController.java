package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jenjangpendidikan/")
public class JenjangPendidikanController {

	@GetMapping("indexapi")
	public String index() {
		return "jenjangpendidikan/indexapi.html";
	}
}
