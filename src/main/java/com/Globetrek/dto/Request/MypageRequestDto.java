package com.Globetrek.dto.Request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MypageRequestDto {
	
	@Getter
	@AllArgsConstructor
	public class MypageResponseDto {
	    private String nickname;
	    private LocalDateTime createdAt;
	    private int wishlistCount;
	    private int scheduledCount;
	    private int postCount;
	}
	
}
