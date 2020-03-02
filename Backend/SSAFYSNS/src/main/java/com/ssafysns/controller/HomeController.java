package com.ssafysns.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//http://localhost/home/settags
@Controller
public class HomeController implements ErrorController{
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
//
	@GetMapping({"/","/error"})
	public String redirect() {
		return "index";
	}
//	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
  
}
