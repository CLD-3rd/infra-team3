package com.Globetrek.service;

import com.Globetrek.dto.Response.UserResponseDto;
import com.Globetrek.entity.User;
import com.Globetrek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto getUserById(Integer userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
        return new UserResponseDto(userId, user.getUserName(), user.getNickname(), user.getCreatedAt());
    }
}
