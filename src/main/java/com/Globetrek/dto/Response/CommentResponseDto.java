package com.Globetrek.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {  
	private Long pid;
	
    @Builder
    public CommentResponseDto( /* 필드들 매개변수로 받기 */ ) {
         this.pid = pid;
    }
}
