package com.Globetrek.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TravelLogResponseDto {  
//	private Long pid;
    private Integer logId;
    private String title;
    private String picUrl;
    private Integer hit;
    private Integer likeCount;
    private Integer commentCount;
    private Integer countryId;
    private String countryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Builder.Default
    private boolean liked = false;
    @Builder.Default
    private boolean wished = false;
	
//    @Builder
//    public TravelLogResponseDto( /* 필드들 매개변수로 받기 */ ) {
//         this.pid = pid;
//    }
}