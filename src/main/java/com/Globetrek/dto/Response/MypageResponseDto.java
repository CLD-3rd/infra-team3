package com.Globetrek.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MypageResponseDto {
    private String nickname;
    private LocalDateTime createdAt;
}
