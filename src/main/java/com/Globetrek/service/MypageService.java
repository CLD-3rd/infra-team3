package com.Globetrek.service;

import com.Globetrek.dto.Request.MypageRequestDto;
import com.Globetrek.dto.Response.MypageResponseDto;
import com.Globetrek.entity.User;
import com.Globetrek.repository.MypageRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class MypageService {

    public MypageResponseDto getUserProfile(String username) {
        // 임시 Mock 유저
        return new MypageResponseDto("mock_nickname", LocalDateTime.now().minusDays(5));
    }

    public void editUser(String username, MypageRequestDto dto) {
    	
    	String mockpass = "1234";
        String mock_nickname = "mock_nickname";
        
        if (!dto.getPassword().equals(mockpass)) {

            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (dto.getNickname().equals(mock_nickname)) {
            throw new IllegalArgumentException("기존 닉네임과 동일합니다.");
        }

        if (dto.getNickname().equals("taken_nickname")) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 실제 저장은 생략 — 나중에 연결만
        System.out.println("닉네임 수정 성공: " + dto.getNickname());
    }
}
