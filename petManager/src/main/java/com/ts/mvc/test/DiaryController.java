package com.ts.mvc.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryController {

	@GetMapping("/diary")
	public String diary() {
		return "/html/diary";
	}
	
	@GetMapping("/diary/modify")
	public String diaryModify() {
		return "/html/diary-modify";
	}
}
