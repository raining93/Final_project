package com.ts.mvc.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterAnimalController {
	
	@GetMapping("/register")
	public String diary() {
		return "/html/register-animal";
	}
	
	@GetMapping("/diary/modify")
	public String diaryModify() {
		return "/html/animal-modify";
	}

}
