package com.Globetrek.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UserResponseDto {
    private Integer userId;
    private String userName;
    private String userNickname;
    private LocalDateTime createdAt;
}