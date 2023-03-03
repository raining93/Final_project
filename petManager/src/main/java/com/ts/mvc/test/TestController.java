package com.ts.mvc.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/")
	public String test() {
		return "/html/index2";
	}
	
	@GetMapping("/register")
	public String register() {
		return "/html/register-animal";
	}
}
