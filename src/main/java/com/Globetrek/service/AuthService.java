package com.Globetrek.service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Globetrek.dto.Request.LoginRequestDto;
import com.Globetrek.dto.Response.UserResponseDto;
import com.Globetrek.entity.User;
import com.Globetrek.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {  // 예: UserService
	private final UserRepository userRepository;
	// 암호화
	private final PasswordEncoder passwordEncoder;
	
	public void registerUser(LoginRequestDto loginRequestDto) {
		if (userRepository.findByUserName(loginRequestDto.getUserName()) != null) {
            throw new IllegalArgumentException("이미 등록된 아이디입니다");
        }
		// 암호화 
		String encodedPassword = passwordEncoder.encode(loginRequestDto.getPassword());
		
		// User 엔티티 생성 시 암호화된 비밀번호 사용
        User user = User.of(
            null,
            loginRequestDto.getUserName(),
            encodedPassword,
            loginRequestDto.getNickname(),
            null
        );

        userRepository.save(user);
		/*
		User user = loginRequestDto.toEntity();

		userRepository.save(user);*/
	}
	
	public Optional<UserResponseDto> searchUser(Integer id) {
		return userRepository.findById(id)
								.map(UserResponseDto::from);
					
	}
    
}
