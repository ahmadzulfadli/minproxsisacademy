package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dokter/")
public class TCariDokterController {
	@GetMapping("caridokter")
	public String index() {
		return "dokter/CariDokter.html";
	}

}
