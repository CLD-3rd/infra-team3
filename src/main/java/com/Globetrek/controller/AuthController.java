package com.Globetrek.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Globetrek.dto.Request.LoginRequestDto;
import com.Globetrek.service.AuthService;

@Controller
@RequestMapping("/auth") 
public class AuthController {
	
	private final AuthService authService;
	

	@Autowired
	public AuthController(AuthService authService) {
		 this.authService = authService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";  
	}


	@PostMapping("/signup")
	public String signup(LoginRequestDto loginRequestDto, Model model) {
		try {
			authService.registerUser(loginRequestDto);
			return "redirect:/auth/login";
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
			return "signup"; // 회원가입 페이지로 다시
		}
	}
	
	@GetMapping("/test")
	public String testPage() {
	    return "test";  
	}
	
}
