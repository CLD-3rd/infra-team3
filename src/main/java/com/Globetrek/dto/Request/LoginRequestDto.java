package com.Globetrek.dto.Request;

import com.Globetrek.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {  
	private String userName;
    private String password;
    private String nickname;

    public User toEntity() {
        return User.of(
                null,
                userName,
                password,
                nickname,
                null  
        );
    }
}