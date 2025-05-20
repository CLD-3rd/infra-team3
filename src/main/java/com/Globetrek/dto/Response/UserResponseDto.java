package com.Globetrek.dto.Response;

import java.time.LocalDateTime;

import com.Globetrek.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "from")
public class UserResponseDto {  
	 private String userName;
	 private String nickname;
	 private LocalDateTime createdAt;

	    public static UserResponseDto from(User user) {
	        return new UserResponseDto(
	                user.getUserName(),
	                user.getNickname(),
	                user.getCreatedAt()
	        );
	    }
	
   
}