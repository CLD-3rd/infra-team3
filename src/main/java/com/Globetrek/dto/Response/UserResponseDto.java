package com.Globetrek.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UserResponseDto {
    private Integer userId;
    private String userName;
    private String userNickname;
    private LocalDateTime createdAt;
}