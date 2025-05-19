package com.Globetrek.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WishListReponseDto {  
	private Long pid;
	
    @Builder
    public WishListReponseDto( /* 필드들 매개변수로 받기 */ ) {
         this.pid = pid;
    }
}