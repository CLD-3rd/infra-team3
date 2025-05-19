package com.Globetrek.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries") 
@RequiredArgsConstructor
public class MainPageController { 
	 @GetMapping("/ping")
	    public String ping() {
	        return "Globetrek API 연결 성공!";
	    }
}
