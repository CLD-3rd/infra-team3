package com.Globetrek.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.Globetrek.dto.Request.MypageRequestDto;
import com.Globetrek.dto.Response.MypageResponseDto;
import com.Globetrek.service.MypageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;
    
    @GetMapping("/view")
    public ModelAndView showPage(HttpSession session) {
        session.setAttribute("username", "mockuser"); // 임시 테스트용
        return new ModelAndView("mypage"); // templates/mypage.html 반환
    }


    // ✅ 마이페이지 기본 정보 조회 API (닉네임, 가입일)
    @GetMapping
    public ResponseEntity<?> getMypageInfo(HttpSession session) {
    	
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }

        MypageResponseDto user = mypageService.getUserProfile(username);
        return ResponseEntity.ok(user);
    }
    
    @PatchMapping("/edit")
    public ResponseEntity<?> updateUser(@RequestBody MypageRequestDto dto, HttpSession session) {
    	
    	System.out.println("PATCH 요청 들어옴1: " + dto.getNickname() + " / " + dto.getPassword());

        
    	session.setAttribute("username", "mockuser");
    	
    	String username = (String) session.getAttribute("username");

    	System.out.println("PATCH 요청 들어옴2: " + dto.getNickname() + " / " + dto.getPassword());

        if (username == null) {
            return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
        }

        try {
            mypageService.editUser(username, dto);
            return ResponseEntity.ok(Map.of("message", "수정 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        
    }

    
}
