package com.mc.mvc.module.member;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.mc.mvc.infra.code.Code;
import com.mc.mvc.infra.util.mail.EmailSender;
import com.mc.mvc.module.member.dto.Principal;
import com.mc.mvc.module.member.dto.request.LoginRequest;
import com.mc.mvc.module.member.dto.request.SignUpRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	private final RestTemplate restTemplate;
	private final EmailSender sender;
	private final PasswordEncoder passwordEncoder;
	
	public boolean existUser(String userId) {
		return memberRepository.existsById(userId);
	}

	public void authenticateEmail(@Valid SignUpRequest form, String authToken) {
		
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("userId", form.getUserId());
		body.put("authToken", authToken);
		body.put("mailTemplate", "signup-email-auth");
		
		RequestEntity<Map<String, Object>> request = 
				RequestEntity
				.post(Code.DOMAIN + "/mail")
				.contentType(MediaType.APPLICATION_JSON)
				.body(body);
		
		ResponseEntity<String> response =  restTemplate.exchange(request, String.class);
		String html = response.getBody();
		
		sender.send(form.getEmail(), "회원가입을 환영합니다. 링크를 클릭해 회원가입을 완료하세요.", html);
	}

	@Transactional
	public void registNewMember(SignUpRequest form) {
		
		form.setPassword(passwordEncoder.encode(form.getPassword()));
		Member member = Member.createMember(form);
		memberRepository.save(member);
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByUserIdAndIsLeave(username, false);
		if(member == null) throw new UsernameNotFoundException(username);
		
		return new UserPrincipal(new Principal(member));
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
