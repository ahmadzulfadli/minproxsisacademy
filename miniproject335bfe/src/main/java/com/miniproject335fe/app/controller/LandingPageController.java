package com.miniproject335fe.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LandingPageController {
	@GetMapping("index")
	public String index() {
		return "index.html";
	}
	@GetMapping("tes")
	public String tes() {
		return "tes/index.html";
	}
	@GetMapping("err")
	public String err() {
		return "tes/404.html";
	}
	@GetMapping("login")
	public String login() {
		return"login/indexlogin.html";
	}
}
