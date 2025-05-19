package com.Globetrek.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TravelLogResponseDto {  
	private Long pid;
	
    @Builder
    public TravelLogResponseDto( /* 필드들 매개변수로 받기 */ ) {
         this.pid = pid;
    }
}