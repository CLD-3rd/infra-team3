package com.Globetrek.dto.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MypageRequestDto {
    private String nickname;
    private String password;
}
