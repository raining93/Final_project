package com.ts.mvc.module.user;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ts.mvc.infra.code.ErrorCode;
import com.ts.mvc.infra.exception.HandlableException;
import com.ts.mvc.module.user.dto.Principal;
import com.ts.mvc.module.user.dto.request.LoginRequest;
import com.ts.mvc.module.user.dto.request.SignUpRequest;
import com.ts.mvc.module.user.validator.SignUpValidator;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

	
	private final SignUpValidator signUpValidator;
	private final UserService userService;
	
	@InitBinder("signUpRequest")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(signUpValidator);
	}
	
	@GetMapping("signup")
	public void signUp(Model model) {
		model.addAttribute("signUpRequest", new SignUpRequest());
	}
	
	@PostMapping("signup")
	public String authenticateEmail(@Valid SignUpRequest form,
								    Errors error,
								    Model model,
								    HttpSession session) {
		
		if(error.hasErrors()) {
			return "/user/signup";
		}
		
		session.setAttribute("signupForm", form);
		
		String authToken = UUID.randomUUID().toString();
		session.setAttribute("authToken", authToken);

		userService.authenticateEmail(form, authToken);
		
		return "redirect:/";
		
	}
	
	@GetMapping("signupimpl/{authToken}")
	public String signUpImpl(HttpSession session,
							 @PathVariable String authToken,
							 @SessionAttribute(name="authToken", required=false) String sessionToken,
							 @SessionAttribute(name="signupForm", required=false) SignUpRequest form,
							 Model model) {
		
		if(!authToken.equals(sessionToken)) {
			throw new HandlableException(ErrorCode.EXPRIATION_SIGNUP_TOKEN);
		}
		
		userService.registNewMember(form);
		
		session.removeAttribute("authToken"); // 만료된 토큰 처리를 위해
		session.removeAttribute("signupForm"); // 메모리 아까워서
		
		return "redirect:/user/login";
	}
	
	@GetMapping("checkId")
	@ResponseBody
	public Map<String, Boolean> checkId(String email) {
		return Map.of("exist", userService.existUser(email));
	}

	@GetMapping("login")
	public void login(Model model) {
		model.addAttribute("loginRequest", new LoginRequest());
	}
	
	@PostMapping("login")
	public String loginImpl(LoginRequest loginRequest,
						    Errors error,
						    HttpSession session,
						    RedirectAttributes redirectAttributes) {
		
		if(error.hasErrors()) {
			return "/user/login";
		}
		
		Principal principal = userService.authenticateUser(loginRequest);
		
		if(principal == null) {
			redirectAttributes.addAttribute("msg", "아이디나 비밀번호가 일치하지 않습니다.");
			
			return "redirect:/user/login";
		}
		
		session.setAttribute("auth", principal);
		
		return "redirect:/";
		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("auth");
		
		return "redirect:/";
	}
	
	@GetMapping("modify")
	public String userModify() {
		return "/html/user-modify";
	}
}
