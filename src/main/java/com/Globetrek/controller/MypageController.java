package com.Globetrek.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Globetrek.dto.Response.MypageResponseDto;
import com.Globetrek.service.MypageService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping
    public String showMypage(@RequestParam String username, Model model) {
        MypageResponseDto user = mypageService.getUserProfile(username);
        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("createdAt", user.getCreatedAt());
        return "mypage"; // → templates/mypage.html 로 렌더링
    }
}
