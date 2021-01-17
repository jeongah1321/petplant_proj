package com.petplant.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/")
	public String main() {
		return "customer/main"; // WEB-INF/views/customer/main.jsp
	}
	
	@RequestMapping("/info")
	public String infomation() {
		return "customer/information"; // WEB-INF/views/customer/information.jsp
	}
	
	@RequestMapping("/gallery")
	public String galley() {
		return "customer/gallery"; // WEB-INF/views/customer/galley.jsp
	}
}
