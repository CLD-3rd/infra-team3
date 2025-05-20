package com.Globetrek.service;

import com.Globetrek.dto.Response.MypageResponseDto;
import com.Globetrek.entity.User;
import com.Globetrek.repository.MypageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final MypageRepository mypageRepository;

    public MypageResponseDto getUserProfile(String username) {
        User user = mypageRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        return new MypageResponseDto(
                user.getNickname(),
                user.getCreatedAt()
        );
    }
}