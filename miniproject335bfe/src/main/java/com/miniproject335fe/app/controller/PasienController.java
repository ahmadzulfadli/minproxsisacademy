package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pasien/")
public class PasienController {
	
	@GetMapping("indexapi")
	public String index() {
		return "pasien/indexapi.html";
	}
	
	@GetMapping("layout")
	public String layout() {
		return "pasien/layoutpasien.html";
	}
	
	@GetMapping("layout2")
	public String layout2() {
		return "pasien/layoutpasien2.html";
	}
	
	@GetMapping("daftarpasien")
	public String daftarpasien() {
		return "pasien/daftarpasien.html";
	}
	
	@GetMapping("profile")
	public String profile() {
		return "pasien/profile.html";
	}
	
	@GetMapping("paymentmethod")
	public String paymentMethod() {
		return "costumerwalletwithdraw/indexlayoutpasien.html";
	}
	
	@GetMapping("alamat")
	public String alamat() {
		return "pasien/alamat.html";
	}
}
